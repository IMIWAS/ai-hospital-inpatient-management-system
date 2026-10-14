import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueDevTools(),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url)),
            '@components': path.resolve(__dirname, './src/components'),
            '@views': path.resolve(__dirname, './src/views'),
            '@utils': path.resolve(__dirname, './src/utils'),
            '@api': path.resolve(__dirname, './src/api'),
            '@assets': path.resolve(__dirname, './src/assets'),
        },
    },
    // 开发服务器配置
    server: {
        port: 3100,              // 端口号（默认 5173）
        open: true,              // 自动打开浏览器
        host: '0.0.0.0',         // 允许外部访问
        https: false,            // 是否启用 HTTPS
        proxy: {                 // 代理配置（解决跨域）
            '/hims/api': {
                target: 'http://localhost:9001',
                changeOrigin: true,
                // rewrite: (path) => path.replace(/^\/hims\/api/, '/hims/api') // 保留 /hims 前缀
            },
        },
    },
    // 构建配置
    build: {
        outDir: 'dist',          // 输出目录
        assetsDir: 'assets',     // 静态资源目录
        sourcemap: false,        // 是否生成 sourcemap（生产环境建议关闭）
        minify: 'esbuild',       // 压缩方式
        chunkSizeWarningLimit: 500, // chunk 大小警告阈值（KB）
    },
})
