<template>
    <el-row>
        <el-col :span="18">
            <el-card class="account" shadow="always">
                <!-- <template #header><el-text tag="b">User Account</el-text></template> -->

                <el-table :data="allAccountInfo" style="width: 100%;">
                    <el-table-column fixed prop="name" label="Name"></el-table-column>
                    <el-table-column prop="accountName" label="Account"></el-table-column>
                    <el-table-column label="Role"
                    :filters="[
                        {text: 'Admin', value:'ADMIN'},
                        {text: 'User', value:'USER'}
                    ]"
                    :filter-method="(value:string, row:AccountInfo) => {return row.role === value}"
                    filter-placement="bottom-end">
                        <template #default="scope">
                            <el-tag style="width: 50px;"
                                :type="scope.row.role === 'ADMIN' ? 'warning' : 'primary'">
                                {{ scope.row.role }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="Operations" min-width="60">
                        <template #default="scope">
                            <el-button link type="primary" size="small" @click="editAccount(scope.row)">Edit</el-button>
                            <el-button link type="primary" size="small" @click="deleteAccount(scope.row)">Delete</el-button>
                        </template>
                    </el-table-column>

                </el-table>

            </el-card>
        </el-col>
        <el-col :span="5">
            <el-affix :offset="100" class="operation" style="max-width: 300px;">
                <el-card>
                    <div class="filter-title"><el-text tag="b" type="primary">Filter</el-text> 
                        <el-button type="primary" size="small" :icon="Search" circle style="float: right;" @click="fetchAccountInfo" /></div>
                    <el-form :model="filterForm" label-width="auto" style="max-width: 100%;">
                        <el-form-item label="Name"><el-input v-model="accountFilterForm.name" size="small"></el-input></el-form-item>
                        <el-form-item label="Account"><el-input v-model="accountFilterForm.accountName" size="small"></el-input></el-form-item>
                        <el-form-item label="Role">
                            <el-radio-group v-model="accountFilterForm.role">
                                <el-radio value="ADMIN">Admin</el-radio>
                                <el-radio value="USER">User</el-radio>
                                <el-radio value=''>无</el-radio>
                            </el-radio-group></el-form-item>
                    </el-form>
                    <el-divider />
                    <el-button style="width: 100%;" text type='primary' class="new-account" @click="newAccountDialogVisible=true">New Account</el-button>
                </el-card>
            </el-affix>
        </el-col>
    </el-row>

    <!-- new Account -->
    <el-dialog
    v-model="newAccountDialogVisible"
    title="New Account"
  >
    <el-form 
      :model="newAccountForm" 
      :rules="accountRules" 
      ref="newAccountFormRef" 
      label-width="auto"
      label-position="right"
    >
      <el-form-item label="Account Name" prop="accountName">
        <el-input 
          v-model="newAccountForm.accountName" 
          placeholder="Enter account name" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Password" prop="password">
        <el-input 
          v-model="newAccountForm.password" 
          type="password" 
          show-password
          placeholder="Enter password" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Role" prop="role">
        <el-radio-group v-model="newAccountForm.role" >
                            <el-radio-button value="ADMIN">Admin</el-radio-button>
                            <el-radio-button value="USER">User</el-radio-button>
                        </el-radio-group>
      </el-form-item>
      
      <el-form-item label="Display Name" prop="name">
        <el-input 
          v-model="newAccountForm.name" 
          placeholder="Enter display name" 
          clearable
        />
      </el-form-item>
    </el-form>
    
    <template #footer>
      <el-button @click="onReset(newAccountFormRef)">Reset</el-button>
      <el-button type="primary" @click="onSubmitNewAccount(newAccountFormRef)">Submit</el-button>
    </template>
  </el-dialog>

  <!-- edit Account -->
  <el-dialog
    v-model="editAccountDialogVisible"
    title="Edit Account"
  >
    <el-form 
      :model="editAccountForm" 
      :rules="accountRules" 
      ref="editAccountFormRef" 
      label-width="auto"
      label-position="right"
    >
      <el-form-item label="Account Name" prop="accountName">
        <el-input 
          v-model="editAccountForm.accountName" 
          placeholder="Enter account name" 
          disabled
        />
      </el-form-item>
      
      <el-form-item label="Password" prop="password">
        <el-input 
          v-model="editAccountForm.password" 
          type="password" 
          show-password
          placeholder="Enter password" 
          clearable
        />
      </el-form-item>
      
      <el-form-item label="Role" prop="role">
        <el-radio-group v-model="editAccountForm.role" >
                            <el-radio-button value="ADMIN">Admin</el-radio-button>
                            <el-radio-button value="USER">User</el-radio-button>
                        </el-radio-group>
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
      <el-button type="primary" @click="onSubmitEditAccount(editAccountFormRef)">Submit</el-button>
    </template>
  </el-dialog>


</template>

<script lang="ts" setup>
import { Search } from '@element-plus/icons-vue'
import { reactive } from 'vue'
import { computed, ref, onMounted } from 'vue' 
import { api } from '../../service/api'
import { ElMessage, ElMessageBox } from 'element-plus'

interface AccountInfo{
    accountName: string,
    role: string,
    name: string,
}

const allAccountInfo = ref<AccountInfo[]>()

interface AccountFilter{
    name: string,
    accountName: string,
    role: string
}
const accountFilterForm = reactive({
    name: null,
    role: null,
    accountName: null,
})

const fetchAccountInfo = async () => {
    if (accountFilterForm.role === ''){
        accountFilterForm.role = null
    }
    const response = await api.post("/admin/account/info", accountFilterForm)
    try{
        if(response.code === 200){
            allAccountInfo.value = response.data
        }
    }catch(error){
        console.log(error)
    }
}

onMounted(() => {
  fetchAccountInfo()
})




interface NewAccountRequest {
  accountName: string | null
  password: string | null
  role: string | null
  name: string | null
}

const newAccountDialogVisible = ref(false)
const newAccountFormRef = ref<FormInstance>()


const newAccountForm = reactive<NewAccountRequest>({
  accountName: null,
  password: null,
  role: null,
  name: null
})


const editAccountDialogVisible = ref(false)
const editAccountFormRef = ref<FormInstance>()


const editAccountForm = reactive<NewAccountRequest>({
  accountName: null,
  password: null,
  role: null,
  name: null
})

const accountRules = reactive<FormRules<NewAccountRequest>>({
  accountName: [
    { required: true, message: 'Please input account name', trigger: 'blur' },
    { min: 3, max: 32, message: 'Length should be 3 to 32 characters', trigger: 'blur' },
  ],
  password: [
    { required: true, message: 'Please input password', trigger: 'blur' },
    { min: 3, max: 32, message: 'Length should be 3 to 32 characters', trigger: 'blur' },
  ],
  role: [
    { required: true, message: 'Please select role', trigger: 'change' }
  ],
  name: [
    { required: true, message: 'Please input display name', trigger: 'blur' },
    { min: 1, max: 32, message: 'Length should be 1 to 32 characters', trigger: 'blur' }
  ]
})


const onSubmitNewAccount = async (formEl: FormInstance | undefined) => {

    if (!formEl) return
    try{
        await formEl.validate()
    }catch (error){
        ElMessage.error('Input Error')
        return
    }

    try{
    const response = await api.post("/admin/account/add", newAccountForm)
    if (response.code === 200){
        ElMessage.success("Add account success")
        fetchAccountInfo()
        if (!formEl) return
        formEl.resetFields()
      }
      else{
        ElMessage.error("Add account fail")
      }
    }catch (error){
      ElMessage.error("Add account fail")
    }

}

const editAccount = async (row) => {
    editAccountForm.accountName = row.accountName
    editAccountForm.name = row.name
    editAccountForm.role = row.role
    editAccountDialogVisible.value = true
}

const onSubmitEditAccount = async (formEl: FormInstance | undefined) => {
    // console.log(formEl)
    if (!formEl) return
    try{
        await formEl.validate()
    }catch (error){
        ElMessage.error('Input Error')
        console.log(error)
        return
    }

    try{
    const response = await api.post("/admin/account/update", editAccountForm)
    if (response.code === 200){
        ElMessage.success("Edit account success")
        fetchAccountInfo()
        editAccountDialogVisible.value = false
      }
      else{
        ElMessage.error("Edit account fail")
      }
    }catch (error){
      ElMessage.error("Edit account fail")
    }

}



const deleteAccount = async (row) => {
  ElMessageBox.confirm(
    `Account ${row.accountName}: ${row.name} will be deleted, continue?`,
    'Warning',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
    }
  ).then(async () =>{
    try{
    const response = await api.post(`/admin/account/delete?accountName=${row.accountName}`)
    if (response.code === 200){
        ElMessage.success("Delete account success")
        fetchAccountInfo()
      }
      else{
        ElMessage.error("Fail: " + response.data)
      }
    }catch (error){
      ElMessage.error("Delete account fail")
    }
  })
    .catch(()=>{ElMessage({
        type: 'info',
        message: 'Delete canceled',
      })})
  
  
}

const filterForm = reactive({
    username: '',
    accountName: '',
    role: ''
})



</script>

<style>
    .account {
        margin-top: 32px;
    }
    .operation {
        margin-left: 16px;
    }
    .new-account {
        margin-top: -20px;
    }
    .filter-title {
        margin-bottom: 16px;
    }
</style>