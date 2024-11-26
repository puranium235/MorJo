<template>
  <div class="content">
    {{ content }}
  </div>
</template>

<script setup>
defineProps({
  content: String,
})
</script>

<style scoped>
.content {
  font-size: 20px;
  word-wrap: break-word;
  white-space: pre-wrap;
  width: 100%;
}
</style>
