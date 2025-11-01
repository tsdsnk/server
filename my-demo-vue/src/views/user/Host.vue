<template>
  <el-row>
    <el-col :span="18">
      <el-card
      v-for="(hostinfo, index) in allHostInfos"
      :id="`host-${index}`"
      shadow="always"
      class="host-card">
      
      <template #header>
        <el-text tag="b"> {{ hostinfo.ip }}</el-text>
        <div style="float: right;">
        <el-button
        v-if="(hostinfo.status === 'APPLYING' || hostinfo.status === 'APPROVED')"
        type="danger"
        round
        size="small"
        @click="onDelete(hostinfo)"
        >
        删除
        </el-button>
        <el-button
        v-else
        round
        size="small"
        @click="onApply(hostinfo)"
        >
        申请
        </el-button>
        </div>
      </template>
        
      
      
      <div class="hosts">
        <el-row :gutter="5" class="host-row">
          <el-col :span="24">
              <el-space :size="20">
                <el-text tag="b" style="width: 60px;" align="right">IP</el-text>
                <el-text style="width: 160px;">{{ hostinfo.ip }}</el-text>
              </el-space>
              <el-space :size="20">
                <el-text tag="b" style="width: 60px;" align="right">端口</el-text>
                <el-text style="width: 160px;">{{ hostinfo.port }}</el-text>
              </el-space>
          </el-col>
        </el-row>

        <el-row :gutter="5" class="host-row">
          <el-col :span="24">
              <el-space :size="20">
                <el-text tag="b" style="width: 60px;" align="right">GPU</el-text>
                <el-text style="width: 160px;">{{ hostinfo.gpuType }} * {{ hostinfo.gpuNumber }}</el-text>
              </el-space>

              <el-space :size="20">
                <el-text tag="b" style="width: 60px;" align="right">显存</el-text>
                <el-text style="width: 160px;">{{ hostinfo.gpuMemory }} GB</el-text>
              </el-space>
              <el-space :size="20">
                <el-text tag="b" style="width: 60px;" align="right">使用人数</el-text>
                <el-text style="width: 160px;">{{ hostinfo.userNumber }}</el-text>
              </el-space>
          </el-col>
        </el-row>

        <el-row :gutter="5" class="host-row">
          <el-col :span="24">
              <el-space :size="20">
                <el-text tag="b" style="width: 60px;" align="right">状态</el-text>
                <el-text style="width: 160px;"
                :type="hostinfo.status === 'APPROVED' ? 'success' : hostinfo.status === 'REJECTED' ? 'danger' : hostinfo.status === 'APPLYING' ? 'warning' : 'info'"
                >
                  {{ hostinfo.status === "APPROVED" ? "通过" : hostinfo.status === "REJECTED" ? "拒绝" : hostinfo.status === 'APPLYING' ? "申请中": "未申请" }}
                </el-text>
              </el-space>
              <el-space :size="20">
                <el-text tag="b" style="width: 60px;" align="right">账号</el-text>
                <el-text style="width: 160px;">{{ hostinfo.applyAccountName ? hostinfo.applyAccountName : "-" }}</el-text>
              </el-space>
              <el-space :size="20">
                <el-text tag="b" style="width: 60px;" align="right">密码</el-text>
                <el-text style="width: 160px;">{{ hostinfo.applyPassword ? hostinfo.applyPassword : "-" }}</el-text>
              </el-space>
          </el-col>
        </el-row>
        <el-row class="host-row">
          <el-col :span="24">
            <el-space :size="20">
                <el-text tag="b" style="width: 60px;" align="right">详情</el-text>
                <el-text style="width: 160px;">{{ hostinfo.message ? hostinfo.message : '-' }}</el-text>
              </el-space>
          </el-col>
        </el-row>
      </div>
      
      </el-card>
    </el-col>
    
    <el-col :span="5">
      <el-affix :offset="100" class="menu" style="max-width: 300px;">
        <el-card>
          <el-anchor >
            <el-text type="info" tag="b" class="menu-title">申请中</el-text><br>
            <template v-for="(hostinfo, index) in allHostInfos">
              <el-anchor-link
              v-if="hostinfo.status === 'APPLYING'"
              :title="hostinfo.ip"
              :href="`#host-${index}`"
              class="menu-item"/>
            </template>
            <el-text type="info" tag="b" class="menu-title">通过</el-text><br>
            <template v-for="(hostinfo, index) in allHostInfos">
              <el-anchor-link
              v-if="hostinfo.status === 'APPROVED'"
              :title="hostinfo.ip"
              :href="`#host-${index}`"
              class="menu-item"/>
            </template>
            <el-text type="info" tag="b" class="menu-title">拒绝</el-text><br>
            <template v-for="(hostinfo, index) in allHostInfos">
              <el-anchor-link
              v-if="hostinfo.status === 'REJECTED'"
              :title="hostinfo.ip"
              :href="`#host-${index}`"
              class="menu-item"/>
            </template>
            <el-text type="info" tag="b" class="menu-title">未申请</el-text>
            <template v-for="(hostinfo, index) in allHostInfos">
              <el-anchor-link
              v-if="!hostinfo.status"
              :title="hostinfo.ip"
              :href="`#host-${index}`"
              class="menu-item"/>
            </template>
          </el-anchor>

        </el-card>
      </el-affix>
    </el-col>
  </el-row>


  <!-- apply dialog -->
  <el-dialog
    v-model="applyDialogVisible"
    title="Apply for Host"
  >
    <el-form 
      :model="applyForm" 
      :rules="applyRules" 
      ref="applyFormRef" 
      label-width="auto"
      label-position="right"
    >
      <el-form-item label="IP" prop="ip">
        <el-input 
          v-model="applyForm.ip" 
          style="width: 100%"
          disabled
        />
      
      </el-form-item>
      
      <el-form-item label="Account Name" prop="accountName">
        <el-input 
          v-model="applyForm.accountName" 
          placeholder="Enter account name for the host" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Password" prop="password">
        <el-input 
          v-model="applyForm.password" 
          type="password" 
          show-password
          placeholder="Enter password for the account" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Message" prop="message">
        <el-input 
          v-model="applyForm.message" 
          type="textarea" 
          :rows="4" 
          placeholder="Optional message or reason for application"
          :maxlength="512"
          show-word-limit
        />
      </el-form-item>
    </el-form>
    
    <template #footer>
      <el-button type="primary" @click="onSubmitApply(applyFormRef)">Submit</el-button>
    </template>
  </el-dialog>

</template>

<script lang="ts" setup>
import { Search } from '@element-plus/icons-vue'
import { reactive } from 'vue'
import { computed, ref, onMounted } from 'vue' 
import { api } from '../../service/api'
import { ElMessage, ElMessageBox } from 'element-plus'

interface HostInfo{
  ip: string,
  accountName: string,
  port: number,
  gpuType: string
  gpuMemory: number
  gpuNumber: number
  cudaVersion: string
  description: string

  status: string
  applyAccountName: string
  applyPassword: string
  message: string
  lastUpdate: Date

  userNumber: number
}



const allHostInfos = ref<HostInfo[]>()

const fetchHostInfo = async () => {
    try{
      const response = await api.get("/user/info")
      if (response.code === 200){
        allHostInfos.value = response.data
        // console.log(response.data)
      }
    }catch (error){
      console.log(error)
    }
}

onMounted(() => {
  fetchHostInfo()
})




const onApply =async (hostinfo:HostInfo) => {
  applyForm.ip = hostinfo.ip
  applyDialogVisible.value = true
}

interface UserApplyRequest {
  ip: string | null
  accountName: string | null
  password: string | null
  message: string | null
}

const applyRules = reactive<FormRules<UserApplyRequest>>({
  accountName: [
    { required: true, message: 'Please input account name', trigger: 'blur' },
    { min: 1, max: 32, message: 'Length should be 1 to 32 characters', trigger: 'blur' },
  ],
  password: [
    { required: true, message: 'Please input password', trigger: 'blur' },
    { min: 1, max: 32, message: 'Length should be 1 to 32 characters', trigger: 'blur' }
  ],
  message: [
    { max: 512, message: 'Message cannot exceed 512 characters', trigger: 'blur' }
  ]
})
const applyDialogVisible = ref(false)
const applyFormRef = ref<FormInstance>()
const availableHosts = ref<Host[]>([])

const applyForm = reactive<UserApplyRequest>({
  ip: null,
  accountName: null,
  password: null,
  message: null
})



const onSubmitApply =  async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    try{
        await formEl.validate()
    }catch (error){
        ElMessage.error('Input Error')
        console.log(error)
        return
    }
    try{
    const response = await api.post("/user/apply", applyForm)
    // console.log(response)
    if (response.code === 200 ){
      if (response.data === 'success'){
        ElMessage.success("Approved")
        applyDialogVisible.value = false
      }else if(response.data.startsWith("Unprocessable:")){
        ElMessage.warning({message:"Submission successful but cannot be processed automatically, please concat the administrator. " + response.data
        , duration:8000})
        applyDialogVisible.value = false
      }else{
        ElMessage.error(response.data)
      }
        
        
      }
      else{
        ElMessage.error("Fail: " + response.data)
      }
      fetchHostInfo()
    }catch (error){
      ElMessage.error("Fail")
    }
}


const onDelete = (hostinfo: HostInfo)=>{

  ElMessageBox.confirm(
    `Application on ${hostinfo.ip}: ${hostinfo.port} [${hostinfo.status}] will be deleted, continue?`,
    'Warning',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
    }
  ).then(()=>{
    onConfirmDelete(hostinfo.ip)
  })
    .catch(()=>{ElMessage({
        type: 'info',
        message: 'Delete canceled',
      })})
}

const onConfirmDelete= async(ip)=>{
  
  try{
    const response = await api.post(`/user/delete?ip=${ip}`)
    if (response.code === 200){
        ElMessage.success("Delete success")
        fetchHostInfo()
      }
      else{
        ElMessage.error("Fail: " + response.data)
      }
      
    }catch (error){
      ElMessage.error("Fail")
    }

}
</script>

<style>
  
  .host-card {
    margin-top: 20px;
  }
  .host-row {
    margin-top: 8px;
  }
  .menu {
    margin-left: 16px;
  }
  .menu-title{
    margin-top: 10px;
    margin-bottom: 10px;
    margin-left: -10px;
  }
  .menu-item{
    margin-left: 10px;
  }
</style>