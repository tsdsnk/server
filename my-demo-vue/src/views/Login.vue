<template>
    <el-card style="max-width: 350px;" class="login-card">
        <el-form ref="formRef" :model="form" :rules="rules" label-width="auto" >
            <el-form-item label="Username" prop="accountName">
                <el-input v-model="form.accountName" />
            </el-form-item>
            <el-form-item label="Password" prop="password">
                <el-input show-password v-model="form.password" />
            </el-form-item>
            <el-form-item >
                <el-row :gutter="20" style="width: 100%;">
                    <el-col  :span="19">
                        <div class="switch-container">
                            <span style="margin-right: 8px;">7天内免登录</span>
                            <el-switch v-model="form.rememberMe" />
                        </div>
                    </el-col>
                    <el-col :span="4">
                        <div class="button-container">
                            <el-button type="primary" @click="login(formRef)">Login</el-button>
                        </div>
                    </el-col>
                </el-row>
            </el-form-item>
        </el-form>
        
    </el-card>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { api } from '../service/api'
import { cookieUtil } from '../service/cookie'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()

interface LoginRequest{
  accountName: string,
  password: string,
  rememberMe: boolean
}

const form = reactive<LoginRequest>({
  accountName: '',
  password: '',
  rememberMe: false
})


const formRef = ref<FormInstance>()
const loading = ref(false)
const rules = reactive<FormRules<LoginRequest>>({
    accountName: [
        {required: true, message: 'Please input Account Name', trigger: 'blur'},
        {min: 1, max:32, message: 'Length should be 1 to 32', trigger: 'blur'}
    ],
    password: [
        {required: true, message: 'Please input Password', trigger: 'blur'},
        {min: 1, max:32, message: 'Length should be 1 to 32', trigger: 'blur'}
    ]
})

const login = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    try{
        await formEl.validate()
    }catch (error){
        ElMessage.error('Input Error')
        return
    }
    loading.value = true;
    try{
        const response = await api.post('/login', form);
        if(response.code === 200){
            // console.log(response)
            cookieUtil.setToken(response.data.jwt, form.rememberMe)
            if(response.data.role === 'ADMIN'){
                router.push('/admin/application')
            }else{
                router.push('/user/host')
            }
        }else{
            ElMessage.error('Login Failed')
        }
    }catch(error){
        ElMessage.error('Login Failed')
    }
    
}

</script>

<style>
    .login-card{
        position: absolute;
        top: 45%;
        left: 50%;
        transform: translate(-50%, -50%); /* 修正自身宽高 */
        width: 400px;        
    }
    .button {
        display: flex;
        justify-content: flex-end; /* 靠右对齐 */
    }   
</style>