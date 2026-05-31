<template>
  <div class="home">
    <h1>Agent Project</h1>
    <p>AI Agent 构建的后台管理系统</p>
    <div v-if="loading">加载中...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else class="status">
      <span :class="health === 'UP' ? 'up' : 'down'">{{ health }}</span>
      后端服务状态
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Home',
  data() {
    return {
      health: null,
      loading: true,
      error: null,
    };
  },
  mounted() {
    axios.get('/api/health')
      .then(res => {
        this.health = res.data.status;
        this.loading = false;
      })
      .catch(err => {
        this.error = '无法连接到后端服务';
        this.loading = false;
      });
  },
};
</script>

<style scoped>
.home {
  text-align: center;
  padding-top: 100px;
}
.status { margin-top: 20px; }
.up { color: green; font-weight: bold; }
.down { color: red; font-weight: bold; }
.error { color: red; }
</style>
