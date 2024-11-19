<template>
  <div class="bar" :style="{ width: ratio }">{{ ratio }}%</div>
</template>

<script setup>
defineProps({
  ratio: Number
})
</script>

<style scoped>
.bar {
  display: flex;
}
</style>
