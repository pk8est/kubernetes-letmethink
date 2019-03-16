<template>
  <div>
    <card>
      <ge-search  :columns="searchConfig" @search="searchHandler" @reset="resetHandler">
        <Button type="success" style="margin-left: 8px" @click="createHandler">新建</Button>
      </ge-search>
      <ge-listgrid ref="listgrid" :columns="listgridConfig" :data="listgrid.data" :total="listgrid.total"
        :buttons="listgridButtons"
        @sort-change="sortChangeHandler"
        @page-change="pageChangeHandler"
        @page-size-change="pageSizeChangeHandler"/>
      <Modal v-model="showModalForm" :title="modalFormTitle">
          <GeModalForm :columns="formConfig" :initForm="initForm" :update="update"/>
          <div slot="footer">
              <Button type="text" size="large" @click="modalCancel">取消</Button>
              <Button type="primary" size="large" @click="formSubmitHandler">提交</Button>
          </div>
      </Modal>
      <Drawer width="640" v-model="showModalView" :title="modalViewTitle">
        <GeView :columns="viewConfig" :data="view"></GeView>
      </Drawer>
    </card>
  </div>
</template>

<script>
import _ from 'lodash'
import emitter from 'iview/src/mixins/emitter'
import GeDay from './ge-day'
import GeTag from './ge-tag'
import GeSearch from './ge-search'
import GeView from './ge-view'
import GeListgrid from './ge-listgrid'
import GeModalForm from './ge-modal-form'
import API from '@/api/cluster'

const statusOpts = [
  {
    value: '1',
    label: '正常',
    color: 'success'
  },
  {
    value: '-1',
    label: '异常',
    color: 'error'
  }
]

export default {
  name: 'GeIndex',
  components: {
    GeDay,
    GeTag,
    GeView,
    GeSearch,
    GeListgrid,
    GeModalForm
  },
  mixins: [emitter],
  data(){
    return {
      view: {},
      modalViewTitle: '',
      showModalView: false,
      update: false,
      showModalForm: false,
      modalFormTitle: "",
      initForm: {},
      queryParam: {},
      sortParam: {},
      pageParam: {
        page: 1,
        size: 10
      },
      listgridButtons: [
        {
          render: (h) => (<Button on-click={ () => this.$refs.listgrid.selectAll() }>全选</Button>)
        },
        {
          name: '删除',
          type: 'error',
          icon: 'md-sync',
          //autoDisabled: false,
          onSelectionChange: ({selection, ids}) => console.info(selection, ids),
          onClick: (event, {selection, ids}) => {
            this.showModalView = true
            console.info(selection, ids)
          }
        },
    {
      name: "查看"
    }

      ],
      configs: [
        {
          name: "",
          listgrid: {
            fixed: 'left',
            width: 50,
            type: 'selection'
          }
        }
        ,{
          column: 'id',
          name: "ID",
          search: true,
          form: {
            geType: 'hidden'
          },
          listgrid: {
            fixed: 'left',
            maxWidth: 100,
            sortable: true
          }
        }
        ,{
          column: 'name',
          name: "名称",
          search: true,
          form: {
            disupdated: true,
            rules: [{required: true, message: '该值不能为空', trigger: 'blur'}]
          }
        }
        ,{
          column: 'clusterMasterUrl',
          name: "Master",
          form: {
            rules: [{required: true, message: '该值不能为空', trigger: 'blur'}]
          },
        }
        ,{
          column: 'clusterUsername',
          name: "用户名",
          search: true,
          form: {
            rules: [{required: true, message: '该值不能为空', trigger: 'blur'}]
          },
          listgrid: {
            sortable: true
          }
        }
        ,{
          column: 'clusterCertKey',
          name: "CertKey",
          view: {
            render: (h, {row, column}) => (<Input type="textarea" rows={10} value={ row[column.key] }/>)
          },
          form: {
            type: 'textarea',
            rows: 5,
            rules: [{required: true, message: '该值不能为空', trigger: 'blur'}]
          },
          listgrid: false
        }
        ,{
          column: 'clusterCertData',
          name: "CertData",
          view: {
            render: (h, {row, column}) => (<Input type="textarea" rows={10} value={ row[column.key] }/>)
          },
          form: {
            type: 'textarea',
            rows: 5,
            rules: [{required: true, message: '该值不能为空', trigger: 'blur'}]
          },
          listgrid: false
        }
        ,{
          column: 'status',
          name: "状态",
          search: {
            clearable: true,
            options: statusOpts
          },
          form: {
            geType: 'radioGroup',
            options: statusOpts
          },
          render: (h, {row, column}) => (<GeTag value={ row[column.key] } options={ statusOpts } />)
        }
        ,{
          column: 'creatorUid',
          name: "创建人",
          form: false,
          search: true
        }
        ,{
          column: 'createdAt',
          name: "创建时间",
          form: false,
          render: (h, {row, column}) => (<GeDay value={ row[column.key] }/>)
        }
        ,{
          column: 'updatedAt',
          name: "更新时间",
          form: false,
          render: (h, {row, column}) => (<GeDay value={ row[column.key] }/>),
          listgrid: {
            show: false
          }
        }
        ,{
          column: 'description',
          name: "描述",
          listgrid: {
            show: false
          }
        }
        ,{
          column: '',
          name: "操作",
          listgrid: {
            fixed: 'right',
            align: 'center',
            width: 180,
            render: (h, {row}) => (
              <div>
                <Button size="small" style="margin-right:5px" on-click={ () => this.viewHandler(row) }>查看</Button>
                <Button size="small" style="margin-right:5px" on-click={ () => this.updateHandler(row) }>更新</Button>
                <Button size="small" style="margin-right:5px" type="error">删除</Button>
              </div>
            )
          }
        }
      ],
      listgrid: {
        total: 0,
        data: []
      },
      /*search: {
        columns: [
          {
            column: 'name',
            label: '名称',
            value: 'xxxx'
          },
          {
            column: 'type',
            label: '类型',
            value: 2,
            filterable: true,
            props: {
              style: "width: 200px"
            },
            options: [{
              value: 1,
              label: (h) => (<div><Icon custom="ivu-icon ivu-icon-ios-bug" size="16" color="red" /> New York</div>)
            },{
              value: 2,
              label: () => '<div>测试</div>'
            },{
              value: 3,
              label: '线上',
              disabled: true
            },{
              value: 4,
              label: (h, context) => {
                return h('div', '开发')
              }
            }],
            on: {
              "on-change": (value) => console.info(value)
            }
          },
          {
            column: 'radio',
            label: () => (<span>RADIO</span>),
            geType: 'radio'
          },
          {
            column: 'radioGroup',
            label: 'RadioGroup',
            geType: 'radioGroup',
            type: 'button',
            vertical: false,
            options: [{
              label: "金斑蝶"
            },{
              label: "爪哇犀牛"
            }],
            labelProps: {
              "label-width": 80
            }
          },
          {
            column: 'checkbox',
            label: 'Checkbox',
            indeterminate: false,
            size: "small",
            geType: 'checkbox',
            hide: true
          },
          {
            column: 'checkboxGroup',
            geType: 'checkboxGroup',
            label: false,
            value: ['a'],
            size: "large",
            hide: true,
            options: [{
              label: "金斑蝶",
              value: 'a'
            },{
              label: "爪哇犀牛",
              value: 'b'
            }]
          },
          {
            column: 'switch',
            value: true,
            size: "default",
            geType: 'switch',
            loading: true,
            hide: true,
            open: () => (<Icon type="md-checkmark"></Icon>)
          },
          {
            column: 'autoComplete',
            geType: 'autoComplete',
            label: false,
            hide: true,
            data: ['apple', 'iphone', 'home'],
            filterMethod: (value, option) => option.toUpperCase().indexOf(value.toUpperCase()) !== -1
          },
          {
            column: 'autoComplete2',
            label: '邮箱',
            geType: 'autoComplete',
            hide: true,
            data: [ '@qq.com', '@163.com', '@sina.com' ],
            template: (h, value) => (<span><Icon custom="ivu-icon ivu-icon-ios-bug"/> { value } </span>)
          },
        ]
      }*/
    }

  },
  computed: {
    viewConfig(){
      let columns = []
      this.configs.map((config, index) => {
        if(config.view !== false && config.column){
          columns.push(_.defaultsDeep({}, config.view, {
            key: config.column,
            title: config.name,
            render: config.render,
            renderHeader: config.renderHeader
          }))
        }
      })
      return columns
    },
    listgridConfig(){
      let columns = []
      this.configs.map((config, index) => {
        if(config.listgrid !== false){
          columns.push(_.defaultsDeep({}, config.listgrid, {
            key: config.column,
            title: config.name,
            render: config.render,
            renderHeader: config.renderHeader
          }))
        }
      })
      return columns
    },
    searchConfig(){
      let columns = []
      this.configs.map((config, index) => {
        if(config.search){
          columns.push(_.defaultsDeep({}, config.search, {
            column: config.column,
            label: config.name
          }))
        }
      })
      return columns
    },
    formConfig(){
      let columns = []
      this.configs.map((config, index) => {
        if(config.form !== false && config.column){
          columns.push(_.defaultsDeep({}, config.form, {
            column: config.column,
            label: config.name
          }))
        }
      })
      return columns
    }
  },
  created () {
    this.listen()
    this.list()
  },
  methods: {
    list () {
      API.list({
        ...this.queryParam,
        ...this.sortParam,
        ...this.pageParam
      }).then(({ data }) => {
        this.listgrid.data = data.list
        this.listgrid.total = data.total
      })
    },
    searchHandler(form) {
      this.queryParam = form
      this.list()
    },
    resetHandler(form){
      this.queryParam = form
      this.list()
    },
    sortChangeHandler({key, order}){
      if(order == 'normal'){
        this.sortParam = {}
      }else{
        this.sortParam['sort'] = key + ',' + order
      }
      this.list()
    },
    pageChangeHandler(page){
      this.pageParam.page = page
      this.list()
    },
    pageSizeChangeHandler(size){
      this.pageParam.size = size
      this.list()
    },
    createHandler(){
      this.update = false
      this.modalFormTitle = "创建"
      this.initForm = {}
      this.showModalForm = true
    },
    viewHandler(row){
      this.view = row
      this.modalViewTitle = "查看: [ " + (row.name || row.id) + " ]"
      this.showModalView = true
    },
    updateHandler(row){
      this.update = true
      this.modalFormTitle = "更新: [ " + (row.name || row.id) + " ]"
      this.initForm = row
      this.showModalForm = true
    },
    modalCancel(){
      this.showModalForm = false
    },
    formSubmitHandler(){
      this.broadcast('GeModalForm', 'submit')
    },
    createdOrUpdatedHandler({status, message, data}){
      if(status == 0){
        this.showModalForm = false
        this.$Message.destroy()
        this.$Message.success(message);
        this.list()
      }else{
        this.$Message.error({content: message, duration: 30, closable: true});
      }
    },
    listen () {
      this.$on('recieve', ({ update, data, valid }) => {
        if(update){
          API.update(data.id, data).then(this.createdOrUpdatedHandler)
        }else{
          API.create(data).then(this.createdOrUpdatedHandler)
        }
      })
    }
  }
}
</script>
