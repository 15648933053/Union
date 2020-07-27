import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/teacher',
    component: Layout,
    redirect: '/teacher/table',
    name: '讲师管理',
    meta: { title: '讲师管理', icon: 'example' },
    children: [
      {
        path: 'table',
        name: '老师列表',
        component: () => import('@/views/edu/teacher/list'),
        meta: { title: '老师列表', icon: 'table' }
      },
      {
        path: 'edit/:id',
        name: 'EduTeacherEdit',
        component: () => import('@/views/edu/teacher/save'),
        meta: { title: '修改老师', noCache: true },
        hidden: true
      }
    ]
  },

  
  {
    path: '/active',
    component: Layout,
    redirect: '/active/table',
    name: '活动管理',
    meta: { title: '活动管理', icon: 'example' },
    children: [
      {
        path: 'table',
        name: '活动列表',
        component: () => import('@/views/edu/active/list'),
        meta: { title: '活动列表', icon: 'table' }
      },
      {
        path: 'save',
        name: '添加活动',
        component: () => import('@/views/edu/active/save'),
        meta: { title: '添加活动', icon: 'tree' }
      },
      {
        path: 'edit/:id',
        name: '修改活动',
        component: () => import('@/views/edu/active/save'),
        meta: { title: '修改活动', noCache: true },
        hidden: true
      }
    ]
  },



  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
