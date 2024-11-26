<template>
  <header class="header">
    <h1 @click="handleToHome" class="pointer">
      <span class="t1 t">너</span>
      <span class="t2 t">만</span>
      <span class="t3 t">모</span>
      <span class="t4 t">르</span>
      <span class="t5 t">조</span>
      <span class="t6 t">?</span>
    </h1>
    <nav>
      <span @click="handleToQuizCreate" class="pointer">문제등록</span>
      <span> | </span>
      <span v-if="!user.isLoggedIn" @click="handleLogin" class="pointer">로그인</span>
      <span v-else @click="handleLogout" class="pointer">로그아웃</span>
      <!-- <span v-if="user.isLoggedIn"> | </span>
      <span v-if="user.isLoggedIn">내정보</span> -->
    </nav>
  </header>
</template>

<script setup>
import router from '@/router';
import { useUser } from '@/stores/user.js'
import { postLogout } from '@/api/userApi.js'

const KAKAO_AUTH_URL = import.meta.env.VITE_KAKAO_AUTH_URL
const REST_API_KEY = import.meta.env.VITE_KAKAO_REST_API_KEY
const REDIRECT_URI = import.meta.env.VITE_KAKAO_REDIRECT_URI

const user = useUser()

const handleLogin = () => {
  location.href  =`${KAKAO_AUTH_URL}?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}`
}

const handleLogout = async () => {
  await postLogout()
  window.location.reload()
}

const handleToQuizCreate = () => {
  router.push({ name: 'create'})
}

const handleToHome = () => {
  router.push({ name: 'home' })
}
</script>

<style scoped>
.header {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 20px 0 12px 0;
  z-index: 1;
}
.pointer {
  cursor: pointer;
}
.t {
  display: inline-block;
}
.t1 {
  transform: translate(-30px, 30px) rotate(-30deg);
}
.t2 {
  transform: translate(0, -3px) rotate(3deg);
}
.t6 {
  transform: translate(20px, -5px) rotate(-20deg);
}
</style>
