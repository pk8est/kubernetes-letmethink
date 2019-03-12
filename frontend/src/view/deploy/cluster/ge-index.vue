<template>
  <div class="config-main">
    <card>
      <ge-search  :columns="searchConfig" @search="searchHandler" @reset="resetHandler"/>
      <ge-listgrid :columns="listgridConfig" :data="listgrid.data" :total="listgrid.total"
        @sort-change="sortChangeHandler"
        @page-change="pageChangeHandler"
        @page-size-change="pageSizeChangeHandler"/>
    </card>
  </div>
</template>

<script>
import _ from 'lodash'
import GeSearch from './ge-search'
import GeListgrid from './ge-listgrid'
import API from '@/api/cluster'

export default {
  name: 'GeIndex',
  components: {
    GeSearch,
    GeListgrid
  },
  data(){
    return {
      queryParam: {},
      sortParam: {},
      pageParam: {
        page: 1,
        size: 10
      },
      configs: [
        {
          name: "",
          listgrid: {
            type: 'selection'
          }
        },
        {
          column: 'id',
          name: "ID",
          search: true,
          listgrid: {
            sortable: true,
            render: (h, { row }) => (<span><Icon custom="ivu-icon ivu-icon-ios-bug" size="16" color="red" />{ row.id }</span>)
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
    listgridConfig(){
      let columns = []
      this.configs.map((config, index) => {
        if(config.listgrid !== false){
          columns.push(_.defaultsDeep({}, config.listgrid, {
            key: config.column,
            title: config.name
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
    }
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
    }
  },
  mounted () {
    this.list()
  }
}
</script>
