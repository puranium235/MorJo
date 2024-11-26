<template>
  <div class="answer" :class="{ selected }" :style="boxStyle">
    <span class="text">{{ option }}</span>
    <div v-show="isResult" class="graph" :style="graphStyle"></div>
    <span v-show="isResult" class="text">{{ showPercent.toFixed(1) }}%</span>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'

const props = defineProps({
  option: String,
  selected: Boolean,
  total: Number,
  votes: Number,
  isResult: Boolean,
  isAnswer: Boolean
})

const percent = computed(() => props.total === 0 ? 0 : (props.votes / props.total * 100).toFixed(1))
const showPercent = ref(0)

const percentInterval = () => {
  const timer = setInterval(() => {
    showPercent.value += 0.35;
    if (showPercent.value > percent.value) {
      showPercent.value = Number(percent.value)
      clearInterval(timer)
    }
  }, 8)
}

watch(() => props.isResult, (newValue) => {
  if (newValue === true) {
    showPercent.value = 0
    percentInterval()
  }
})

const boxStyle = computed(() => {
  const GREEN = '#4CAF50'
  const RED = '#D14D72'
  const defaultShadow = '2px 2px 3px rgba(0, 0, 0, 0.1)'

  return {
    border: props.isResult ? `${props.isAnswer ? GREEN : RED } 2px solid` : props.selected ? `${GREEN} 2px solid` : 'none',
    boxShadow: props.selected ? `0 0 10px ${props.isResult && !props.isAnswer ? RED : GREEN}` : defaultShadow,
    cursor: props.isResult ? 'default' : 'pointer'
  }
})

const graphStyle = computed(() => {
  const PINK = '#FCC8D1'
  const LIGHTGREEN = '#DFF2E1'
  return {
    width: `${ showPercent.value }%`,
    backgroundColor: props.isAnswer ? LIGHTGREEN : PINK
  }
})


</script>

<style scoped>
.answer {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 50px;
  padding: 12px;
  font-size: 16px;
  border-radius: 4px;
  background-color: #ffffff;
}
.text {
  z-index: 1;
}
.graph {
  position: absolute;
  top: 0;
  left: 0;
  width: 0;
  height: 100%;
  border-radius: 4px;
}
</style>
