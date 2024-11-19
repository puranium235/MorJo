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
}
</style>
