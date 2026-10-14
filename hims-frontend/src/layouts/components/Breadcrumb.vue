<template>
  <el-breadcrumb separator="/" class="breadcrumb">
    <el-breadcrumb-item v-for="item in breadcrumbs" :key="item.path">
      <span v-if="item.redirect || item.path === currentPath">{{ item.meta?.title }}</span>
      <a v-else @click.prevent="handleLink(item)">{{ item.meta?.title }}</a>
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const breadcrumbs = ref([])
const currentPath = ref('')

function getBreadcrumbs() {
  currentPath.value = route.path
  let matched = route.matched.filter(item => item.meta && item.meta.title)

  if (!matched.length) {
    breadcrumbs.value = []
    return
  }

  const first = matched[0]
  if (!isDashboard(first)) {
    matched = [
      { path: '/dashboard', meta: { title: '首页' } }
    ].concat(matched)
  }

  breadcrumbs.value = matched.filter(item => item.meta && item.meta.title !== false)
}

function isDashboard(route) {
  const name = route && route.name
  if (!name) return false
  return name.toString().trim().toLowerCase() === 'dashboard'
}

function handleLink(item) {
  const { redirect, path } = item
  if (redirect) {
    router.push(redirect)
    return
  }
  router.push(path)
}

watch(() => route.path, getBreadcrumbs, { immediate: true })
</script>

<style lang="scss" scoped>
.breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 60px;

  :deep(a) {
    color: #909399;
    cursor: pointer;

    &:hover {
      color: #0d9488;
    }
  }

  :deep(span) {
    color: #303133;
    font-weight: 500;
  }
}
</style>
