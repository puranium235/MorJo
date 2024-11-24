<template>
  <div class="container">
    <div class="title">닉네임을 정해주세요</div>
    <div class="nickname-container">
      <input type="text" v-model="nickname" class="nickname">
      <div v-if="error" class="error">닉네임을 다시 설정해주세요</div>
    </div>
    <button class="register" @click="handleRegisterClick">가입하기</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { postJoin } from '@/api/userApi.js'
import router from '@/router/index.js'

const nickname = ref('')
const error = ref(false)

const handleRegisterClick = async () => {
  try {
    await postJoin(nickname.value)
    await router.push({
      name: 'home'
    })
  } catch {
    error.value = true
  }

}
</script>

<style scoped>
.container {
  padding: 80px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  width: 30%;
  min-width: 350px;
  box-shadow: 0 0 3px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(50px);
}

.title {
  font-size: 24px;
  padding: 20px 0;
}

.nickname-container {
  display: flex;
  flex-direction: column;
  width: 100%;
}
.nickname {
  width: 100%;
  height: 52px;
  padding: 0 8px;
  font-size: 20px;
  box-shadow: 2px 2px 3px rgba(0, 0, 0, 0.1);
  border-radius: 4px;
}

.error {
  font-size: 12px;
  color: red;
}

.register {
  width: 100%;
  height: 52px;
  border-radius: 4px;
  background-color: #000000;
  color: #ffffff;
  font-size: 16px;
}

.register:hover {
  background-color: rgba(0, 0, 0, 0.8);
}
</style>
