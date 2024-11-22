<template>
  <div class="option-input-wrapper">
    <input type="text" :value="value" :placeholder="placeholder" />
  </div>
</template>

<script setup>
import { defineProps } from 'vue'

defineProps({
  value: String,
  placeholder: String
})

</script>

<style scoped>
.option-input-wrapper {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  width: 100%;
}

input {
  padding: 12px;
  font-size: 16px;
  border: 1px solid;
  width: 100%;
  height: 50px;
}

input::placeholder {
  font-size: 16px;
}
</style>
