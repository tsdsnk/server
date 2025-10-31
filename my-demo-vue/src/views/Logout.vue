<template>
    <h3>Log Out</h3>
</template>

<script>
import { computed, ref, onMounted } from 'vue' 
import { api } from '../service/api'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()
onMounted(async ()=>{
    try{
        await api.get("/logout")
    }catch (error){
        ElMessage.error("Something wrong")
    }
    router.push("/login")
})
</script>