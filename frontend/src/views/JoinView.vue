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
import { useUser } from '@/stores/user.js'

const nickname = ref('')
const error = ref(false)
const user = useUser()

const handleRegisterClick = async () => {
  try {
    await postJoin(nickname.value)
    user.login()
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
  padding-top: 80px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  width: 20%;
  min-width: 250px;
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
  aspect-ratio: 7;
  padding: 0 8px;
  font-size: 16px;
}

.error {
  font-size: 12px;
  color: red;
}

.register {
  width: 100%;
  aspect-ratio: 7;
  border: 1px solid #000000;
}

.register:hover {
  border: 2px solid;
}
</style>
