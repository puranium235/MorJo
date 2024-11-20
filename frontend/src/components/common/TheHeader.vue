<template>
  <header class="header">
    <h1>너만모르조?</h1>
    <nav>
      <span>문제등록 | </span>
      <span v-if="!user.isLoggedIn" @click="handleLogin" class="login">로그인</span>
      <span v-else>로그아웃</span>
      <span v-if="user.isLoggedIn"> | 내정보</span>
    </nav>
  </header>
</template>

<script setup>
import { useUser } from '@/stores/user.js'

const user = useUser()

const KAKAO_AUTH_URL = import.meta.env.VITE_KAKAO_AUTH_URL
const REST_API_KEY = import.meta.env.VITE_KAKAO_REST_API_KEY
const REDIRECT_URI = import.meta.env.VITE_KAKAO_REDIRECT_URI

const handleLogin = () => {
  location.href  =`${KAKAO_AUTH_URL}?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}`
}
</script>

<style scoped>
.header {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;

  padding: 20px 0 12px 0;
}
.login {
  cursor: pointer;
}
</style>
