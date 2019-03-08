<template>
  <div class="config-main">
    <card>
      <ge-search  v-bind='search'/>
      <ge-listgrid />
    </card>
  </div>
</template>

<script>
import GeSearch from './ge-search'
import GeListgrid from './ge-listgrid'

export default {
  name: 'GeIndex',
  components: {
    GeSearch,
    GeListgrid
  },
  data(){
    return {
      search: {
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
            type: 'radio'
          },
          {
            column: 'radioGroup',
            label: 'RadioGroup',
            type: 'radioGroup',
            button: 'button',
            vertical: false,
            options: [{
              label: "金斑蝶"
            },{
              label: "爪哇犀牛"
            }]
          },
          {
            column: 'checkbox',
            label: 'Checkbox',
            indeterminate: false,
            size: 50,
            type: 'checkbox'
          },
          {
            column: 'checkboxGroup',
            type: 'checkboxGroup',
            value: '2',
            size: 30,
            options: [{
              label: "金斑蝶"
            },{
              label: "爪哇犀牛"
            }]
          }
        ]
      }
    }

  }
}
</script>
