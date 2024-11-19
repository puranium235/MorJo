<template>
  <div class="main">
    <the-header></the-header>
    <router-view></router-view>
  </div>
</template>

<script setup>
import TheHeader from '@/components/common/TheHeader.vue'

</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
  gap: 12px;
  align-items: center;
  justify-content: center;
}
</style>
