<template>
  <button class="button" :style="boxStyle">
    <span class="text">{{ value }}</span>
    <div v-show="isResult" class="graph" :style="graphStyle"></div>
    <span class="text">{{ isResult ? ` ${showPercent.toFixed(1)} %` : ''}}</span>
  </button>
</template>

<script setup>
import { computed, ref, watch } from 'vue'

const props = defineProps({
  value: String,
  selected: Boolean,
  total: Number,
  votes: Number,
  isResult: Boolean,
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
    border: `2px solid ${props.isResult && props.selected ? "#2196F3" : "#dcdcdc"}`,
    boxShadow: (props.selected ? "0 0 10px" : "0 0 0") + " " + (props.isResult ? "#2196F3": "#616161"),
    cursor: props.isResult ? 'default' : 'pointer'
  }
})

const graphStyle = computed(() => {
  return {
    height: `${ showPercent.value }%`,
    backgroundColor: props.selected ? "#E3F2FD" : "#E9E9E9",
  }
})

</script>

<style scoped>
.button {
  position: relative;
  display: flex;
  gap: 8px;
  justify-content: center;
  align-items: center;
  flex-basis: 50%;
  aspect-ratio: 2;
  background-color: #ffffff;

  font-size: 24px;
  border-radius: 2px;
}
.text {
  z-index: 1;
}
.graph {
  position: absolute;
  bottom: 0;
  left: 0;
  height: 0;
  width: 100%;
}
</style>
