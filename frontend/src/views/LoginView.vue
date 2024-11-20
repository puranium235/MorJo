<template>
  <div>로그인 중 ...</div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useUser } from '@/stores/user.js'
import router from '@/router/index.js'
import { useRoute } from 'vue-router'

const route = useRoute()
const user = useUser()

onMounted(() => {
  if (route.query.isValidUser) {
    user.login()
    router.push({
      name: 'home'
    })
  }
})
</script>

<style scoped>

</style>
