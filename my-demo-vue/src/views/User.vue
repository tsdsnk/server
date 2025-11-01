<template>
  <div class="header-container">
    <el-menu
      :router="true"
      class="el-menu-demo"
      mode="horizontal"
    >
      <el-menu-item index="host">Host</el-menu-item>
    </el-menu>
    <el-button 
      
      text 
      @click="alterAccountDialogVisible=true" 
      :size="18"
      class="logout-btn"
    >
    <el-icon><UserFilled /></el-icon></el-button>

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
   

  <el-dialog
    v-model="alterAccountDialogVisible"
    title="Alter Account Info"
    style="width: 500px;"
  >
    <el-form 
      :model="editAccountForm" 
      :rules="accountRules" 
      ref="alterAccountFormRef" 
      label-width="auto"
      label-position="right"
    >
      
      <el-form-item label="Password" prop="password">
        <el-input 
          v-model="editAccountForm.password" 
          type="password" 
          show-password
          placeholder="Enter password" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Display Name" prop="name">
        <el-input 
          v-model="editAccountForm.name" 
          placeholder="Enter display name" 
          clearable
        />
      </el-form-item>
    </el-form>
    
    <template #footer>
      <el-button type="primary" @click="commitAlterInfo(alterAccountFormRef)">Submit</el-button>
    </template>
  </el-dialog>

</template>

<script setup lang="ts">
import { api } from '../service/api'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { useRouter } from 'vue-router'
import { reactive } from 'vue'
import { computed, ref, onMounted } from 'vue' 


const activeIndex = ref('1')
const router = useRouter()
const handleLogout = async ()=>{
    try{
        await api.get("/logout")
    }catch (error){
        ElMessage.error("Something wrong")
    }
    router.push("/login")
}


interface AlterAccountRequest{
  accountName: string | null
  password: string | null
  name: string | null
}
const alterAccountDialogVisible = ref(false)
const alterAccountFormRef = ref<FormInstance>()
const editAccountForm = reactive<AlterAccountRequest>({
  accountName: null,
  password: null,
  name: null
})
const accountRules = reactive<FormRules<AlterAccountRequest>>({
  password: [
    { required: true, message: 'Please input password', trigger: 'blur' },
    { min: 3, max: 32, message: 'Length should be 3 to 32 characters', trigger: 'blur' },
  ],
  name: [
    { required: true, message: 'Please input display name', trigger: 'blur' },
    { min: 1, max: 32, message: 'Length should be 1 to 32 characters', trigger: 'blur' }
  ]
})
const commitAlterInfo =async (formEl: FormInstance | undefined) => {
  if (!formEl) return
    try{
        await formEl.validate()
    }catch (error){
        ElMessage.error('Input Error')
        return
    }

    try{
    const response = await api.post("/alter-info", editAccountForm)
    if (response.code === 200){
        ElMessage.success("success")
        alterAccountDialogVisible.value=false
      }
      else{
        ElMessage.error("fail "+ response.data)
      }
    }catch (error){
      ElMessage.error("fail")
    }
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