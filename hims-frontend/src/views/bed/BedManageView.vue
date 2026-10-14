<template xmlns:el-table="http://www.w3.org/1999/html">
    <div class="page-container">
        <div class="page-card">
            <!-- TODO 学生实现：床位统计概览 -->
          <el-row :gutter="20" class="overview-row">
            <el-col :span="6" class="overview-col total">
              <div class="card-icon">
                <el-icon>
                  <CircleCheck>

                  </CircleCheck>
                </el-icon>
              </div>
              <div class="card-label">总床数</div>
              <div class="card-value">{{bedStas.total}}</div>
            </el-col>
            <el-col :span="6" class="overview-col occupied">
              <div class="card-icon">
                <el-icon>
               <DataAnalysis></DataAnalysis>
                </el-icon>
              </div>
              <div class="card-label">空床数</div>
              <div class="card-value">{{bedStas.occipued}}</div>
            </el-col>
            <el-col :span="6" class="overview-col empty">
              <div class="card-icon">
                <el-icon>
                  <DataAnalysis></DataAnalysis>
                </el-icon>
              </div>
              <div class="card-label">空床率</div>
              <div class="card-value">{{bedStas.empty}}</div>
            </el-col>
                <el-col :span="6" class="overview-col rate">
                  <div class="card-icon">
                    <el-icon>
                      <DataAnalysis></DataAnalysis>
                    </el-icon>
                  </div>
                  <div class="card-label">使用率</div>
                  <div class="card-value">{{bedStas.rate}}</div>
<!--                </el-col>-->
<!--              </el-row>-->
          </el-col>
          </el-row>
          <el-form style="margin: 20px 0;":model="query" inline label-width="80px">
            <el-form-item label="关键词">
              <el-input style="width:200px;" v-model="query.keyword" placeholder="请输入关键词" @keyup.enter = "hanleSearch()"></el-input>
                </el-form-item>
                  <el-form-item label="科室">
                    <el-select style="width: 120px;" v-model="query.department" placeholder="请选择科室">
                      <el-option label="内科" value="内科"></el-option>
                      <el-option label="外科" value="外科"></el-option>
                      <el-option label="骨科" value="骨科"></el-option>
                      <el-option label="儿科" value="儿科"></el-option>
                      <el-option label="icu" value="icu"></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="状态">
                    <el-select style="width: 120px;" v-model="query.bedStatus" placeholder="请选择状态">
                      <el-option v-for="(s,index) in BED_STATUS_OPTIONS" :key="index" :label="s.lable" :value="s.value"></el-option>/
                    </el-select>
                  </el-form-item>
          </el-form>





          <div class="toolbar">
            <el-button type="primary">
              <el-icon>
                <Search/>
              </el-icon>
              搜索
            </el-button>
            <el-button type="default">
              <el-icon>
                <Refresh/>
              </el-icon> 刷新
            </el-button>
          </div>

            <!-- TODO 学生实现：分页 -->
          <el-table :data="dataList" border stripe v-loading="loading" empty-text="暂无床位信息">
            <el-table-column prop="bedNo" label="床位号"></el-table-column>
            <el-table-column prop="roomNo" label="病房号"></el-table-column>
            <el-table-column prop="department" label="科室"></el-table-column>
            <el-table-column prop="floor" label="楼层"></el-table-column>
            <el-table-column prop="bedType" label="床位类型"></el-table-column>
            <el-table-column prop="bedStatus" label="床位状态">
              <template #default="{row}">{{getBedStatus(row)}}</template>
            </el-table-column>
            <el-table-column prop="bedPrice" label="床位费用"></el-table-column>
            <el-table-column prop="remark" label="备注"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column label="操作" fixed="right">
              <template #default="{row}">
                <el-button type="text" @click="handleEdit(row)">编辑</el-button>
                <el-button type="text" @click="handleDelete(row)">删除</el-button>
              </template>
            </el-table-column>

          </el-table>
          <el-pagination style="margin-top: 16px;justify-content: flex-end;" v-model:current-page="query.pageNum" v-model:default-current-page="query.pageSize" :size="[10,20,30,40]" :total="total"
                         layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange()" @current-change="handleCurrentChange">

          </el-pagination>

        </div>

        <!-- TODO 学生实现：编辑弹窗 -->
      <BedFormDialog v-model="dialogVisible" :data="currentItem" @success="loadData()">

      </BedFormDialog>
    </div>
</template>

<script setup>
import BedFormDialog from "@/views/bed/components/BedFormDialog.vue";
// TODO
import {bedApi}  from "@/api/bed.js";
import {BED_STATUS_OPTIONS} from "@/utils/dict.js";
import {CircleCheck, DataAnalysis, DataBoard, DataLine, Refresh, Search} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {onMounted, ref} from "vue";
const bedStas=ref({
  total:10,
  occipued:20,
  empty:'54%',
  rate:'89%'
})
const query=ref({
  pageNum:1,
  pageSize:10,
  keyword:null,
  bedStatus:null,
  department:null,

})
const loading =ref(false);
const dataList = ref([
  {
    bedNo: 'A01',
    roomNo: '301',
    department: '内科',
    floor: '3楼',
    bedType: '普通床位',
    bedStatus: 0, // 0空床
    bendPrice: 35,
    remark: '靠窗床位',
    createTime: '2026-08-10 08:30:00'
  },
  {
    bedNo: 'A02',
    roomNo: '301',
    department: '内科',
    floor: '3楼',
    bedType: '普通床位',
    bedStatus: 1, //1占用
    bendPrice: 35,
    remark: '',
    createTime: '2026-08-12 09:10:00'
  },
  {
    bedNo: 'B01',
    roomNo: '402',
    department: '外科',
    floor: '4楼',
    bedType: 'VIP床位',
    bedStatus: 2, //2维修
    bendPrice: 120,
    remark: '床垫损坏，等待维修',
    createTime: '2026-08-05 14:20:00'
  },
  {
    bedNo: 'C03',
    roomNo: '205',
    department: '儿科',
    floor: '2楼',
    bedType: '儿童床位',
    bedStatus: 3, //3预留
    bendPrice: 45,
    remark: '预留给急诊入院患儿',
    createTime: '2026-09-20 16:45:00'
  },
  {
    bedNo: 'A03',
    roomNo: '302',
    department: '内科',
    floor: '3楼',
    bedType: '普通床位',
    bedStatus: 0,
    bendPrice: 35,
    remark: '',
    createTime: '2026-09-01 10:00:00'
  },
  {
    bedNo: 'D02',
    roomNo: '503',
    department: '骨科',
    floor: '5楼',
    bedType: '护工陪护床位',
    bedStatus: 1,
    bendPrice: 50,
    remark: '术后病人',
    createTime: '2026-09-15 11:25:00'
  }
])
const total =ref(0)
function handleSize(){
  loadData()
}
function handleCurrentChange(){
  loadData()
}

function handleSearch(){
  query.value.pageNum = 1
  loadData()
}
async  function loadData(){
  loading.value=true
  try {
    const data=await bedApi.getList(query.value)
    total.value  = data.total
    dataList.value  = data.list
    console.log('data数据',data)
  }finally {
    loading.value=false
  }
}
function getBedStatus(row){
  return BED_STATUS_OPTIONS.find(e=>e.value==row.bedStatus)?.label
}
onMounted( ()=>{
  loadData()
})
const dialogVisible=ref(false)
const currentItem=ref(null)

function handleEdit(row){
  currentItem.value={...row}
  dialogVisible.value=true;
}
function handleDelete(row){
  ElMessageBox.confirm(`确认删除床位 ${row.bedNo}？`, '提示', { type: 'warning' })
    .then(() => {
      ElMessage.success('删除成功')
      loadData()
    }).catch(() => {})
}
</script>

<style lang="scss" scoped>
/* 学生按需补充样式 */
.overview-col {
  display: flex;
  align-items: center;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0px 2px 12px rgba(0,  0, 0, 1);
  color: #fff;

  .card-icon{
    font-size: 40px;
    margin-right: 16px;
  }
  .card-label{
    font-size: 14px;
    font-weight: 500;
    margin:0px 8px;
  }
  .card-value{
    font-size: 28px;
    font-weight: 700;
    line-height: 1.2;
  }
  &.total {
    background: linear-gradient(135deg, #8e8e93 0%, #636366 100%);
  }

  &.occupied {
    background: linear-gradient(135deg, #f97316 0%, #c2410c 100%);
  }

  &.empty {
    background: linear-gradient(135deg, #52c41a 0%, #389e0d 100%);
  }

  &.rate {
    background: linear-gradient(135deg, #fa8c16 0%, #d46b08 100%);
  }
}
.toolbar{
  margin-bottom: 16px;
}
</style>
