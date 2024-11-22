<template>
  <button class="button" :style="boxStyle">
    <span>{{ value }}</span>
    <span>{{ isResult ? ` ${showPercent.toFixed(1)} %` : ''}}</span>
    <div v-show="isResult" class="graph" :style="graphStyle"></div>
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
    border: props.isResult && props.selected ? "2px solid #2196F3" : "1px solid #000000",
    boxShadow: props.isResult && props.selected ? "0 0 10px #2196F3" : "none",
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
  flex-basis: 50%;
  aspect-ratio: 2;
  border: 1px solid #000000;

  font-size: 24px;
}
.graph {
  position: absolute;
  z-index: -1;
  bottom: 0;
  left: 0;
  width: 100%;
}
</style>
