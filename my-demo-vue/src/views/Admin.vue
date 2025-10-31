<template>
  <div class="header-container">
    <el-menu
      :router="true"
      class="el-menu-demo"
      mode="horizontal"
    >
    <el-menu-item index="application">Application</el-menu-item>
    <el-menu-item index="host">Host</el-menu-item>
    <el-menu-item index="account">Account</el-menu-item>
    </el-menu>
    <el-button 
      type="danger" 
      text 
      @click="handleLogout" 
      class="logout-btn"
    >
      <el-icon :size="22"><SwitchButton /></el-icon>
    </el-button>
  </div>
   <el-row class="main" justify="start">
    <el-col :span="24">
      <router-view></router-view>
    </el-col>
  </el-row>
  <el-backtop :right="100" :bottom="100" />
   
</template>

<script setup lang="ts">
import { ref } from 'vue'


const activeIndex = ref('1')
import { api } from '../service/api'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
const router = useRouter()
const handleLogout = async ()=>{
    try{
        await api.get("/logout")
    }catch (error){
        ElMessage.error("Something wrong")
    }
    router.push("/login")
}
</script>

<style>
body {
  margin: 0;
}
.header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  padding: 0 20px;
}
.main {
  padding: 20px;
}
.logout-btn {
  margin-left: auto; /* 确保按钮靠右 */
}
.el-menu--horizontal {
  border-bottom: none;
  flex: 1; /* 让菜单占据剩余空间 */
}
.el-menu--horizontal .el-menu-item {
  flex-shrink: 0; /* 防止菜单项被压缩 */
}
</style>