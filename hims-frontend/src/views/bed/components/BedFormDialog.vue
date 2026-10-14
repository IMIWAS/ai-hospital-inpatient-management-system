<template>
    <el-dialog v-model="visible" :title="isEdit?'编辑':'新增'" width="600px"  @close="handleClose()">
        <!-- TODO 学生实现：表单 -->
      <el-form :model="form" ref="formRef" :rules="rules" label-width="120px">
        <el-form-item label="床位号" prop="bedNo">
          <el-input v-model ="form.bedNo" placeholder="请输入床位号" clearable>

          </el-input>
        </el-form-item>
        <el-form-item label="病床号">
          <el-input v-model="form.roomNo" placeholder="请输入病床号" clearable>

          </el-input>
        </el-form-item>
        <el-form-item label="楼层" >
          <el-input v-model ="form.floor" placeholder="请输入楼层" clearable>

          </el-input>
        </el-form-item>
        <el-form-item label="科室">
          <el-select style="width: 120px;" v-model="form.department" placeholder="请选择科室">
            <el-option label="内科" value="内科"></el-option>
            <el-option label="外科" value="外科"></el-option>
            <el-option label="骨科" value="骨科"></el-option>
            <el-option label="儿科" value="儿科"></el-option>
            <el-option label="icu" value="icu"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="bedStaus">
          <el-select style="width: 120px;" v-model="form.bedStatus" placeholder="请选择状态">
            <el-option v-for="(s,index) in BED_STATUS_OPTIONS" :key="index" :label="s.label" :value="s.value"></el-option>/
          </el-select>
        </el-form-item>
        <el-form-item label="床位类型" prop="bedType">
          <el-select style="width:120px;" v-model="form.bedType"placeholder="请选择床位状态">
            <el-option v-for="(t,index) in BED_TYPE" :key="index" :label="t":value="t"></el-option>
          </el-select>

        </el-form-item>
        <el-form-item label="床位日费">
          <el-input-number v-model="form.bedPrice" :min="0" :precision="2"></el-input-number>
          <span style="margin-left: 8px;">元/天</span>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="2"></el-input>
        </el-form-item>

      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit()">确定</el-button>
      </template>
    </el-dialog>

</template>

<script setup>
// TODO 学生实现
import {bedApi} from "@/api/index.js";
import {BED_STATUS_OPTIONS,BED_TYPE} from "@/utils/dict.js";
import {computed, reactive, ref, watch} from "vue";

import {formatDate} from "@utils/date.js";

const props=defineProps({
  modelValue:Boolean,
  data:Object
})
const form = reactive({
  bedNo:null,
  roomNo:null,
  department:null,
  floor:null,
  bedType:null,
  bedStatus:null,
  bedPrice:null,
  remark:null,
})
const formRef=ref(false)
const rules ={
  bedNo:[{required:true,message:'请输入床位号',trigger:'blur'}],
  bedType:[{required:true,message:'请输入床位类型',trigger:'change'}],
  bedStatus:[{required:true,message:'请输入床位状态',trigger:'change'}],

}
const visible=ref(props.modelValue)
const isEdit=computed(()=>!!props.data?.bedId)
const emit=defineEmits(['update:modelValue','success'])

watch(visible,(val)=>emit('update:modelValue',val))
watch(()=>props.modelValue,(nval)=>{
  visible.value=nval
  if (nval){
    if (isEdit){
      Object.assign(form,props.data)
    }
  }
},{immediate:true})

async function handleSubmit(){
  await formRef.value.validate()
  try{
    if (isEdit.value){
      await bedApi.update(form)
    }else{
      await bedApi.create(form)
    }
    visible.value=false
    emit('success')
  }finally {

  }
}

const handleClose = ()=>{
  visible.value = false
}
</script>

<style lang="scss" scoped>
/* 学生按需补充样式 */
</style>
