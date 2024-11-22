<template>
  <div class="answer" :class="{ selected }" :style="boxStyle" @mouseup="handleMouseUp">
    <span class="option">{{ option }}</span>
    <div v-show="isResult" class="graph" :style="graphStyle"></div>
    <span v-show="isResult">{{ showPercent.toFixed(1) }}%</span>
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
  return {
    border: (props.isResult ? ((props.isAnswer ? '#4CAF50' : '#D14D72') + " 2px") : '#000000 1px') + " solid",
    boxShadow: (props.selected ? "0 0 10px" : "0 0 0") + " " + (props.isResult ? (props.isAnswer ? "#4CAF50" : "#D14D72") : '#616161'),
    cursor: props.isResult ? 'default' : 'pointer'
  }
})

const graphStyle = computed(() => {
  return {
    width: `${ showPercent.value }%`,
    backgroundColor: props.isAnswer ? '#DFF2E1' : '#FCC8D1'
  }
})


</script>

<style scoped>
.answer {
  position: relative;
  display: flex;
  justify-content: space-between;
  height: 50px;
  line-height: 25px;
  padding: 12px;
  font-size: 16px;
}
.graph {
  position: absolute;
  z-index: -1;
  top: 0;
  left: 0;
  width: 0;
  height: 100%;
}
</style>
