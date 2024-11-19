<template>
  <button class="button" :class="{ selected }">
    {{ value }}
  </button>
</template>

<script setup>
defineProps({
  value: String,
  selected: Boolean,
})
</script>

<style scoped>
.button {
  flex-basis: 50%;
  aspect-ratio: 2;
  border: 1px solid #000000;

  font-size: 24px;
}
.button:hover, .selected {
  border: 2px solid;
}
</style>
