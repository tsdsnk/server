<template>
    <el-row>
        <el-col :span="18">
            <el-card
            v-for="(host, index) in allHosts"
            :id="`host-${index}`"
            shadow="always"
            class="host-card">
            <template #header >
                <el-text tag="b"> {{ host.name }}</el-text>
                <div style="float: right;">
                <el-button
                round
                size="small"
                @click="onEdit(host)"
                >
                修改
                </el-button>
                </div>
            </template>
            <div class="hosts">
                    <el-row :gutter="5" class="host-row">
                    <el-col :span="24">
                        <el-space :size="20">
                            <el-text tag="b" style="width: 60px;" align="right">IP</el-text>
                            <el-text style="width: 160px;">{{ host.ip }}</el-text>
                        </el-space>
                        <el-space :size="20">
                            <el-text tag="b" style="width: 60px;" align="right">端口</el-text>
                            <el-text style="width: 160px;">{{ host.port }}</el-text>
                        </el-space>
                    </el-col>
                    </el-row>

                    <el-row :gutter="5" class="host-row">
                    <el-col :span="24">
                        <el-space :size="20">
                            <el-text tag="b" style="width: 60px;" align="right">GPU</el-text>
                            <el-text style="width: 160px;">{{ host.gpuType }} * {{ host.gpuNumber }}</el-text>
                        </el-space>

                        <el-space :size="20">
                            <el-text tag="b" style="width: 60px;" align="right">显存</el-text>
                            <el-text style="width: 160px;">{{ host.gpuMemory }} GB</el-text>
                        </el-space>

                        <el-space :size="20">
                            <el-text tag="b" style="width: 60px;" align="right">CUDA</el-text>
                            <el-text style="width: 160px;">{{ host.cudaVersion }}</el-text>
                        </el-space>
                        
                    </el-col>
                    </el-row>

                    <el-row class="host-row">
                    <el-col :span="24">
                        <el-space :size="20">
                            <el-text tag="b" style="width: 60px;" align="right">详情</el-text>
                            <el-container ><el-text truncated>{{ host.description }}</el-text></el-container>
                        </el-space>
                    </el-col>
                    </el-row>
                </div>
            
            </el-card>
        </el-col>
        <el-col :span="5">
            <el-affix :offset="100" class="menu" style="max-width: 300px;">
                <el-card>
                <el-anchor>
                    
                    <el-anchor-link
                    v-for="(host, index) in allHosts"
                    :title="host.name"
                    :href="`#host-${index}`"
                    class="menu-item"/>

                </el-anchor>
                <el-divider />
                <el-button style="width: 100%;" text type='primary' class="new-host" @click="newHostDialogVisible=true">New Host</el-button>
                </el-card>
            </el-affix>
        </el-col>

    </el-row>

    <!-- New Host Dialog -->
    <el-dialog
      v-model="newHostDialogVisible"
      title="New Host"

    >
    <el-form :model="newHostForm" :rules="rules" ref="newHostFormRef" label-width="auto">
      <el-text class="form-title" type="primary" size="large" tag="b">Network Configuration</el-text>
      
      <el-form-item label="IP Address" prop="ip">
        <el-input 
          v-model="newHostForm.ip" 
          placeholder="Enter IPv4 or IPv6 address" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Host Name" prop="name">
        <el-input 
          v-model="newHostForm.name" 
          placeholder="Enter host name" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Port" prop="port">
        <el-input-number 
          v-model="newHostForm.port" 
          :min="0" 
          :max="65535" 
          controls-position="right"
          placeholder="Port number"
          style="width: 200px;"
        />
      </el-form-item>

      <el-text class="form-title" type="primary" size="large" tag="b">GPU Configuration</el-text>
      <el-form-item label="GPU Type" prop="gpuType">
        <el-input 
          v-model="newHostForm.gpuType" 
          placeholder="e.g. RTX 4090, A100, etc." 
          clearable
        />
      </el-form-item>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="GPU Memory (GB)" prop="gpuMemory">
            <el-input-number 
              v-model="newHostForm.gpuMemory" 
              :min="0" 
              controls-position="right"
              placeholder="Memory in GB"
              style="width: 200px;"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="GPU Number" prop="gpuNumber">
            <el-input-number 
              v-model="newHostForm.gpuNumber" 
              :min="1" 
              controls-position="right"
              placeholder="Number of GPUs"
              style="width: 200px;"
            />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-form-item label="CUDA Version" prop="cudaVersion">
        <el-input 
          v-model="newHostForm.cudaVersion" 
          placeholder="e.g. 12.0, 11.8, etc." 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Description" prop="description">
        <el-input 
          v-model="newHostForm.description" 
          type="textarea" 
          :rows="3" 
          placeholder="Optional description about the host"
          :maxlength="512"
          show-word-limit
        />
      </el-form-item>

      <el-text class="form-title" type="primary" size="large" tag="b">Admin Configuration</el-text>
      
      <el-form-item label="Admin Port" prop="adminPort">
        <el-input-number 
          v-model="newHostForm.adminPort" 
          :min="0" 
          :max="65535" 
          controls-position="right"
          placeholder="Admin port number"
        />
      </el-form-item>
      
      <el-form-item label="Admin Account" prop="adminAccountName">
        <el-input 
          v-model="newHostForm.adminAccountName" 
          placeholder="Admin account name" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Admin Password" prop="adminPassword">
        <el-input 
          v-model="newHostForm.adminPassword" 
          type="password" 
          show-password
          placeholder="Admin password" 
          clearable
        />
      </el-form-item>

    </el-form>
    <template #footer>
      <el-button type="primary" @click="onSubmitNewHost(newHostFormRef)">Submit</el-button>
        <el-button @click="onReset(newHostFormRef)">Reset</el-button>
    </template>

    </el-dialog>

    <!-- Edit Host Dialog -->
    <el-dialog
      v-model="editHostDialogVisible"
      title="New Host"

    >
    <el-form :model="editHostForm" :rules="rules" ref="editHostFormRef" label-width="auto">
      <el-text class="form-title" type="primary" size="large" tag="b">Network Configuration</el-text>
      
      <el-form-item label="IP Address" prop="ip">
        <el-input 
          v-model="editHostForm.ip" 
          disabled
        />
      </el-form-item>
      
      <el-form-item label="Host Name" prop="name">
        <el-input 
          v-model="editHostForm.name" 
          placeholder="Enter host name" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Port" prop="port">
        <el-input-number 
          v-model="editHostForm.port" 
          :min="0" 
          :max="65535" 
          controls-position="right"
          placeholder="Port number"
          style="width: 200px;"
        />
      </el-form-item>

      <el-text class="form-title" type="primary" size="large" tag="b">GPU Configuration</el-text>
      <el-form-item label="GPU Type" prop="gpuType">
        <el-input 
          v-model="editHostForm.gpuType" 
          placeholder="e.g. RTX 4090, A100, etc." 
          clearable
        />
      </el-form-item>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="GPU Memory (GB)" prop="gpuMemory">
            <el-input-number 
              v-model="editHostForm.gpuMemory" 
              :min="0" 
              controls-position="right"
              placeholder="Memory in GB"
              style="width: 200px;"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="GPU Number" prop="gpuNumber">
            <el-input-number 
              v-model="editHostForm.gpuNumber" 
              :min="1" 
              controls-position="right"
              placeholder="Number of GPUs"
              style="width: 200px;"
            />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-form-item label="CUDA Version" prop="cudaVersion">
        <el-input 
          v-model="editHostForm.cudaVersion" 
          placeholder="e.g. 12.0, 11.8, etc." 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Description" prop="description">
        <el-input 
          v-model="editHostForm.description" 
          type="textarea" 
          :rows="3" 
          placeholder="Optional description about the host"
          :maxlength="512"
          show-word-limit
        />
      </el-form-item>

      <el-text class="form-title" type="primary" size="large" tag="b">Admin Configuration</el-text>
      
      <el-form-item label="Admin Port" prop="adminPort">
        <el-input-number 
          v-model="editHostForm.adminPort" 
          :min="0" 
          :max="65535" 
          controls-position="right"
          placeholder="Admin port number"
        />
      </el-form-item>
      
      <el-form-item label="Admin Account" prop="adminAccountName">
        <el-input 
          v-model="editHostForm.adminAccountName" 
          placeholder="Admin account name" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Admin Password" prop="adminPassword">
        <el-input 
          v-model="editHostForm.adminPassword" 
          type="password" 
          show-password
          placeholder="Admin password" 
          clearable
        />
      </el-form-item>

    </el-form>
    <template #footer>
      <el-button type="primary" @click="onSubmitEditHost(editHostFormRef)">Submit</el-button>
    </template>

    </el-dialog>


</template>

<script lang="ts" setup>
import { computed, ref, onMounted, reactive } from 'vue' 
import { api } from '../../service/api'

interface Host{
  ip: string,
  port: number,
  name: string,
  gpuType: string,
  gpuMemory: number,
  gpuNumber: number,
  cudaVersion: string,
  description: string
}


const allHosts = ref<Host[]>()
const fetchHosts = async () => {
  try{
      const response = await api.post("/admin/host/info", hostFilterForm)
      if (response.code === 200){
        allHosts.value = response.data
        // console.log(response.data)
      }
    }catch (error){
      console.log(error)
    }
}

interface HostFilter{
  ip: string,
  port: number,
  name: string
}

const hostFilterForm = reactive({
  ip: null,
  port: null,
  name: null,
})

onMounted(() => {
  fetchHosts()
})

/** new Host */
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
const newHostDialogVisible = ref(false)
const newHostFormRef = ref<FormInstance>()
interface newHostRequest{
  ip: string | null,
  name: string| null,
  port: number| null,
  gpuType: string| null,
  gpuMemory: number| null,
  gpuNumber: number| null,
  cudaVersion: string| null,
  description: string| null,
  adminPort: number| null,
  adminAccountName: string| null,
  adminPassword: string| null
}
const newHostForm = reactive<newHostRequest>({
  ip: null,
  name: null,
  port: null,
  gpuType: null,
  gpuMemory: null,
  gpuNumber: null,
  cudaVersion: null,
  description: null,
  adminPort: null,
  adminAccountName: null,
  adminPassword: null
})
const editHostDialogVisible = ref(false)
const editHostFormRef = ref<FormInstance>()
const editHostForm = reactive<newHostRequest>({
  ip: null,
  name: null,
  port: null,
  gpuType: null,
  gpuMemory: null,
  gpuNumber: null,
  cudaVersion: null,
  description: null,
  adminPort: null,
  adminAccountName: null,
  adminPassword: null
})

const rules = reactive<FormRules<newHostRequest>>({
  ip: [
    { required: true, message: 'Please input IP Address', trigger: 'blur' },
    { 
      pattern: /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$|^(?:(?:(?:[0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(?:(?:[0-9A-Fa-f]{1,4}:){1,7}:)|(?:(?:[0-9A-Fa-f]{1,4}:){1,6}:[0-9A-Fa-f]{1,4})|(?:(?:[0-9A-Fa-f]{1,4}:){1,5}(?::[0-9A-Fa-f]{1,4}){1,2})|(?:(?:[0-9A-Fa-f]{1,4}:){1,4}(?::[0-9A-Fa-f]{1,4}){1,3})|(?:(?:[0-9A-Fa-f]{1,4}:){1,3}(?::[0-9A-Fa-f]{1,4}){1,4})|(?:(?:[0-9A-Fa-f]{1,4}:){1,2}(?::[0-9A-Fa-f]{1,4}){1,5})|(?:[0-9A-Fa-f]{1,4}:(?::[0-9A-Fa-f]{1,4}){1,6})|(?::(?::[0-9A-Fa-f]{1,4}){1,7}|:))$/,
      message: 'Please enter a valid IPv4 or IPv6 address',
      trigger: 'blur'
    }
  ],
  name: [
    { required: true, message: 'Please input Host Name', trigger: 'blur' },
    { min: 1, max: 32, message: 'Length should be 1 to 32 characters', trigger: 'blur' }
  ],
  port: [
    { required: true, message: 'Please input Port', trigger: 'blur' },
    { 
      type: 'number', 
      min: 0, 
      max: 65535, 
      message: 'Port must be between 0 and 65535', 
      trigger: 'blur' 
    }
  ],
  gpuType: [
    { required: true, message: 'Please input GPU Type', trigger: 'blur' },
    { min: 1, max: 64, message: 'Length should be 1 to 64 characters', trigger: 'blur' }
  ],
  gpuMemory: [
    { required: true, message: 'Please input GPU Memory', trigger: 'blur' },
    { 
      type: 'number', 
      min: 0, 
      message: 'GPU Memory must be greater than or equal to 0', 
      trigger: 'blur' 
    }
  ],
  gpuNumber: [
    { required: true, message: 'Please input GPU Number', trigger: 'blur' },
    { 
      type: 'number', 
      min: 1, 
      message: 'GPU Number must be at least 1', 
      trigger: 'blur' 
    }
  ],
  cudaVersion: [
    { required: true, message: 'Please input CUDA Version', trigger: 'blur' },
    { min: 1, max: 16, message: 'Length should be 1 to 16 characters', trigger: 'blur' }
  ],
  description: [
    { max: 512, message: 'Description cannot exceed 512 characters', trigger: 'blur' }
  ],
  adminPort: [
    { required: true, message: 'Please input Admin Port', trigger: 'blur' },
    { 
      type: 'number', 
      min: 0, 
      max: 65535, 
      message: 'Admin Port must be between 0 and 65535', 
      trigger: 'blur' 
    }
  ],
  adminAccountName: [
    { required: true, message: 'Please input Admin Account Name', trigger: 'blur' },
    { min: 1, max: 32, message: 'Length should be 1 to 32 characters', trigger: 'blur' }
  ],
  adminPassword: [
    { required: true, message: 'Please input Admin Password', trigger: 'blur' },
    { min: 1, max: 32, message: 'Length should be 1 to 32 characters', trigger: 'blur' }
  ]
})

const onReset = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const onSubmitNewHost = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
    try{
        await formEl.validate()
    }catch (error){
        ElMessage.error('Input Error')
        return
    }

  try{
    const response = await api.post("/admin/host/add", newHostForm)
    if (response.code === 200){
        ElMessage.success("Add host success")
        fetchHosts()
        if (!formEl) return
        formEl.resetFields()
      }
      else{
        ElMessage.error("Add host fail")
      }
    }catch (error){
      ElMessage.error("Add host fail")
    }
}



const onEdit = async(host: Host) => {
  editHostForm.ip = host.ip
  editHostForm.name = host.name
  editHostForm.port = host.port
  editHostForm.gpuType = host.gpuType
  editHostForm.gpuMemory = host.gpuMemory
  editHostForm.gpuNumber = host.gpuNumber
  editHostForm.cudaVersion = host.cudaVersion
  editHostForm.description = host.description

  editHostDialogVisible.value = true
}


const onSubmitEditHost = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
    try{
        await formEl.validate()
    }catch (error){
        ElMessage.error('Input Error')
        return
    }

  try{
    const response = await api.post("/admin/host/update", editHostForm)
    if (response.code === 200){
        ElMessage.success("Edit host success")
        fetchHosts()
        if (!formEl) return
        formEl.resetFields()
      }
      else{
        ElMessage.error("Edit host fail")
      }
    }catch (error){
      ElMessage.error("Edit host fail")
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
    margin-top: 8px;
    margin-bottom: 8px;
  }
  .menu-item{
    margin-left: -5px;
  }
  .new-host{
    margin-top: -20px;
  }
  .form-title{
    display: block;
    margin-top: 2px;
    margin-bottom: 10px;
    margin-left: 5px;
    padding: 10px 0;
  }
</style>