<template>
<el-row>
        <el-col :span="18">
            <el-card class="application" shadow="always">
                <!-- <template #header><el-text tag="b">User Account</el-text></template> -->

                <el-table :data="allApplications" style="width: 100%;">
                    <el-table-column prop="lastUpdate" label="Date" sortable :formatter="dateFormatter"></el-table-column>
                    <el-table-column label="Name">
                        <template #default="scope">
                        <el-tooltip :content="scope.row.accountName" translate="slide-fade">
                            {{ scope.row.name }}
                        </el-tooltip>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ip" label="Host"></el-table-column>
                    <el-table-column label="Status"
                    :filters="[
                        {text: '申请', value:'APPLYING'},
                        {text: '通过', value:'APPROVED'},
                        {text: '拒绝', value:'REJECTED'}
                    ]"
                    :filter-method="(value:string, row:Application) => {return row.status === value}"
                    filter-placement="bottom-end">
                        <template #default="scope">
                            <el-text style="width: 160px;"
                            :type="scope.row.status === 'APPROVED' ? 'success' : scope.row.status === 'REJECTED' ? 'danger' : 'warning' "
                            >
                            {{ scope.row.status === "APPROVED" ? "通过" : scope.row.status === "REJECTED" ? "拒绝" : "申请" }}
                            </el-text>
                        </template>
                    </el-table-column>
                    <el-table-column label="ApplyAccount">
                        <template #default="scope">
                        <el-tooltip :content="scope.row.applyPassword" translate="slide-fade">
                            {{ scope.row.applyPassword }}
                        </el-tooltip>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="Operations" min-width="60">
                        <template #default="scope">
                            <div v-if="scope.row.status === 'APPLYING'">
                                <el-button link type="primary" @click="approve(scope.row)">通过</el-button>
                                <el-button link type="primary" @click="reject(scope.row)">拒绝</el-button>
                            </div>
                            <div v-else>
                                <el-button link type="primary"  @click="remove(scope.row)">删除</el-button>
                            </div>
                        </template>
                    </el-table-column>

                </el-table>

            </el-card>
        </el-col>
        <el-col :span="6">
            <el-affix :offset="100" class="operation" style="max-width: 300px;">
                <el-card>
                    <div class="filter-title"><el-text tag="b" type="primary">Filter</el-text> 
                        <el-button type="primary" size="small" :icon="Search" circle style="float: right;" @click="fetchApplication"/></div>
                    <el-form :model="applicationFilterForm" label-width="auto" style="max-width: 100%;">
                        <el-form-item label="From">
                            <el-date-picker
                                v-model="applicationFilterForm.from"
                                type="date"
                                placeholder="Start date"
                                clearable
                            />
                        </el-form-item>
                        <el-form-item label="To" >
                            <el-date-picker
                                v-model="applicationFilterForm.to"
                                type="date"
                                placeholder="End date"
                                clearable
                            />
                        </el-form-item>
                        
                        <el-form-item label="Account">
                            <el-select v-model="applicationFilterForm.accountName" >
                                <el-option 
                                v-for="account in allAccountInfo" 
                                :label="account.name"
                                :value= "`${account.accountName}`"
                                />
                                <el-option label="无" value=''/>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="Host">
                            <el-select v-model="applicationFilterForm.ip" >
                                <el-option 
                                v-for="host in allHosts" 
                                :label="host.name"
                                :value= "`${host.ip}`"
                                />
                                <el-option label="无" value=''/>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="Status">
                            <el-radio-group v-model="applicationFilterForm.status">
                                <el-radio value="APPLYING">申请</el-radio>
                                <el-radio value="APPROVED">通过</el-radio>
                                <el-radio value="REJECTED">拒绝</el-radio>
                                <el-radio value="">无</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-form>
                    
                </el-card>
            </el-affix>
        </el-col>
    </el-row>
</template>

<script lang="ts" setup>
import { formatter, type TableColumnCtx } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { reactive } from 'vue'
import { computed, ref, onMounted } from 'vue' 
import { api } from '../../service/api'
import { ElMessage } from 'element-plus'

interface Application{
    accountName: string,
    name: string,
    ip: string,
    port: number,
    status: string,
    applyAccountName: string,
    applyPassword: string,
    message: string,
    lastUpdate: Date
}

interface ApplicationFilter{
    from: Date,
    to: Date,
    accountName: string,
    ip: string,
    status: string,
}

onMounted(()=>{
    fetchApplication()
    fetchHosts()
    fetchAccountInfo()
})
const applicationFilterForm = reactive({
    from: null,
    to: null,
    accountName: null,
    ip: null,
    status: null
})


const fetchApplication = async () =>{
    if (applicationFilterForm.status === ''){
        applicationFilterForm.status = null
    }
    const response = await api.post("/admin/application/info", applicationFilterForm)
    try{
        if(response.code === 200){
            allApplications.value = response.data
            // console.log(response)
        }
    }catch(error){
        console.log(error)
    }
}



interface Host{
  ip: string,
  port: number,
  name: string,
  gpuType: string,
  gpuMemory: number,
  gpuNumber: number,
  cudaVersion: number,
  description: string
}


const allHosts = ref<Host[]>()
const fetchHosts = async () => {
  try{
      const response = await api.post("/admin/host/info", {})
      if (response.code === 200){
        allHosts.value = response.data
        // console.log(response.data)
      }
    }catch (error){
      console.log(error)
    }
}
interface AccountInfo{
    accountName: string,
    role: string,
    name: string,
}

const allAccountInfo = ref<AccountInfo[]>()

const fetchAccountInfo = async () => {
    const response = await api.post("/admin/account/info", {})
    try{
        if(response.code === 200){
            allAccountInfo.value = response.data
        }
    }catch(error){
        console.log(error)
    }
}



const dateFormatter = (row: Application, column: TableColumnCtx<Application>) => {
    const date = new Date(row.lastUpdate)
    return `${date.getFullYear()}-${date.getMonth()}-${date.getDay()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`
}

const allApplications = ref<Application[]>()


const approve = async(row: Application) => {
    
    try{
        const response = await api.post(`/admin/application/approve?accountName=${row.accountName}&ip=${row.ip}`)
        if(response.code === 200){
            ElMessage.success(`Approve user[${row.name}] on ${row.ip}: Success`)
        }else{
            ElMessage.error(`Approve user[${row.name}] on ${row.ip}: Fail`)
        }
    }catch (error){
        ElMessage.error(`Approve user[${row.name}] on ${row.ip}: Fail`)
    }
    fetchApplication()
}
const reject = async(row: Application) => {
    try{
        const response = await api.post(`/admin/application/reject?accountName=${row.accountName}&ip=${row.ip}`)
        if(response.code === 200){
            ElMessage.success(`Rejcet user[${row.name}] on ${row.ip}: Success`)
        }else{
            ElMessage.error(`Reject user[${row.name}] on ${row.ip}: Fail`)
        }
    }catch (error){
        ElMessage.error(`Reject user[${row.name}] on ${row.ip}: Fail`)
    }
    fetchApplication()
}
const remove = async(row: Application) => {
    try{
        console.log(`/admin/application/delete?accountName=${row.accountName}&ip=${row.ip}`)
        const response = await api.post(`/admin/application/delete?accountName=${row.accountName}&ip=${row.ip}`)
        if(response.code === 200){
            ElMessage.success(`Delete user[${row.name}] on ${row.ip}: Success`)
        }else{
            ElMessage.error(`Delete user[${row.name}] on ${row.ip}: Fail`)
        }
    }catch (error){
        ElMessage.error(`Delete user[${row.name}] on ${row.ip}: Fail`)
    }
    fetchApplication()
}

interface Host{
    name: string
    host_ip: string
    host_port: string
}


</script>

<style>
    .application {
        margin-top: 32px;
    }
    .operation {
        margin-left: 16px;
    }
    .filter-title {
        margin-bottom: 16px;
    }
</style>