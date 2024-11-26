<template>
  <div class="container">
    <div class="title">존재하지 않는 페이지입니다</div>
    <div class="content">잠시후 홈으로 이동합니다</div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import router from '@/router/index.js'

onMounted(() => {
  setTimeout(() => {
    router.replace({ name: 'home' })
  }, 2000)
})
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  gap: 40px;
  align-items: center;
  padding-top: 100px;
}
.title {
  font-size: 40px;
  font-weight: 1000;
  text-align: center;
}
.content {
  font-size: 30px;
}
</style>
