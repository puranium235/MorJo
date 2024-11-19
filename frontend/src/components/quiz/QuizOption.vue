<template>
  <div class="answer" :class="{ selected }" >
    {{ option }}
  </div>
</template>

<script setup>
defineProps({
  option: String,
  selected: Boolean,
})
</script>

<style scoped>
.answer {
  height: 50px;
  line-height: 25px;
  padding: 12px;
  font-size: 16px;
  border: 1px solid #000000;
  cursor: pointer;
}
.answer:hover, .selected {
  border: 2px solid;
}
</style>
