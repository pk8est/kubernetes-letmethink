<template>
    <div>
      <Table ref="currentTable" :columns="showColumns" :data="data" v-bind="$attrs"
      @on-sort-change="sortChangeHandler"
      @on-selection-change="selectionChangeHandler"
      v-on="on" />
      <Row style="margin-top: 15px">
        <Col span="12" push="12">
          <Button shape="circle" icon="md-settings" @click="settingDrawer = true" style="float: right"></Button>
          <Page :total="total"
            show-sizer
            show-elevator
            style="float: right; margin-right: 20px"
            :page-size-opts="pageSizeOpts"
            @on-change="pageChangeHandler"
            @on-page-size-change="pageSizeChangeHandler" />
        </Col>
        <Col span="12" pull="12" class="button-row">
          <span v-for="(button, index) in buttons">
            <slot v-if="button.render" :name="'button-' + index">{{ renderItem(button.render, 'button-' + index) }}</slot>
            <Button v-else :type="button.type"
              @click="selectionButtonClickHandler"
              ><Icon :type="button.icon" v-if="button.icon" size="14"/> {{button.name}} </Button>
          </span>
        </Col>
      </Row>

      <Drawer title="显示设置" :closable="false" v-model="settingDrawer" width="200">
          <p class="show-setting" v-for="column in setting"><Checkbox v-model="column.show" size="large">&nbsp;&nbsp;{{ column.title }}</Checkbox></p>
      </Drawer>
    </div>
</template>

<script>
export default {
  name: 'GeListgrid',
  components: { },
  props: {
      columns: {
          default: () => []
      },
      total: 0,
      defaultShow: {
        default: () => true
      },
      data: {
          default: () => []
      },
      buttons: {
          default: () => []
      },
      pageSizeOpts: {
          default: () => [10, 20, 50, 100]
      },
      on: {
          default: () => {}
      }
  },
  data(){
    return {
      selectOn: false,
      selection: [],
      settingDrawer: false,
      setting: this.initSetting()
    }
  },
  computed: {
    showColumns(){
      return this.columns.filter((column) => {
        return !this.setting[column.key] || this.setting[column.key].show == true
      })
    }
  },
  methods: {
    initSetting(){
      let setting = {}
      this.columns.map((column) => {
        if(column.key){
          setting[column.key] = {
            show: column.show !== undefined ? column.show : this.defaultShow,
            title: column.title
          }
        }
      })
      return setting
    },
    sortChangeHandler(column){
      this.$emit("sort-change", column)
    },
    pageChangeHandler(page){
      this.$emit("page-change", page)
    },
    pageSizeChangeHandler(size){
      this.$emit("page-size-change", size)
    },
    renderItem(fn, name, ...args){
      this.$slots[name] = typeof fn == 'function' ? fn(this.$createElement, ...args) : fn
    },
    selectAll(){
      this.selectOn = !this.selectOn
      this.$refs.currentTable.selectAll(this.selectOn)
    },
    selectionChangeHandler(selection){
      this.selection = selection
      for(let button of this.buttons){
        if(button.onSelectionChange){
          button.onSelectionChange(event, { selection: this.selection, ids: this.selection.map((item) => item.id) })
        }
      }
    },
    selectionButtonClickHandler(event){
      for(let button of this.buttons){
        if(button.onClick){
          button.onClick(event, { selection: this.selection, ids: this.selection.map((item) => item.id) })
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.show-setting{
  line-height: 30px;
}
.button-row{
  button{
    margin-right: 8px;
  }
}
</style>
