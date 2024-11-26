<template>
  <div class="main">
    <div class="background"></div>
    <the-header></the-header>
    <router-view></router-view>
    <img src="./assets/bonobono.png" alt="" class="left">
    <img src="./assets/bonobono.png" alt="" class="right">
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
  justify-content: flex-start;
  min-height: 100vh;
}
.background {
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1;
  width: 100vw;
  height: 200vh;
  background: linear-gradient(140deg, red, orange, yellow, green, blue, indigo, purple);
}
.left {
  position: fixed;
  left: 0;
  top: 0;
  z-index: -1;
  transform: rotate(180deg);
  width: 40%;
}
.right {
  position: fixed;
  right: 0;
  bottom: 0;
  z-index: -1;
  width: 40%;
}
</style>
