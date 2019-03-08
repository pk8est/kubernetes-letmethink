<template>
  <div class="config-main">
    <Card>
      <Form  ref="searchForm" :model="searchForm"  :label-width="50">
        <Row :gutter="20">
          <Col span="6">
            <FormItem label="名称" >
              <Input v-model="searchForm.name" placeholder /></Input>
            </FormItem>
          </Col>
          <Col span="6">
            <Button type="primary" @click="search" style="margin-right: 16px;">搜索</Button>
            <Button type="primary" @click="resetSearch" style="margin-right: 16px;">重置</Button>
            <Button type="success" @click="showCreate = true" style="margin-right: 16px;">新建</Button>
          </Col>
        </Row>
      </Form>
      <Row>
        <Table border :columns="columns" :data="data.list" :loading="tableLoading"/>
      </Row>
    </Card>

    <Modal
      v-model="showViewModal"
      title="新建"
      :width="840"
    >
      <Form  ref="form" :model="form" :rules="rules" :label-width="100">
        <FormItem label="集群名" prop="name">
          <Input v-model="form.name" placeholder /></Input>
        </FormItem>
        <FormItem label="MasterURL" prop="clusterMasterUrl">
          <Input v-model="form.clusterMasterUrl" placeholder/></Input>
        </FormItem>
        <FormItem label="用户名" prop="clusterUsername">
          <Input v-model="form.clusterUsername" placeholder/></Input>
        </FormItem>
        <FormItem label="CertKey" prop="clusterCertKey">
          <Input v-model="form.clusterCertKey" type="textarea" placeholder/></Input>
        </FormItem>
        <FormItem label="CertData" prop="clusterCertData">
          <Input v-model="form.clusterCertData" type="textarea" placeholder/></Input>
        </FormItem>
        <FormItem label="description" prop="remark">
          <Input v-model="form.description" placeholder/></Input>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button type="success" size="large" @click="create">提交</Button>
      </div>
    </Modal>
  </div>
</template>

<script type="text/ecmascript-6">
import dayjs from 'dayjs'
import API from '@/api/cluster'

export default {
  components: {},
  methods: {
    resetSearch () {
      this.$refs['searchForm'].resetFields()
      this.list()
    },
    search () {
      this.list()
    },
    list () {
      this.tableLoading = true
      API.list(this.searchForm).then(({ data }) => {
        this.data.list = data.list
        this.tableLoading = false
      })
    },
    get(id){
      API.get(id).then(({data}) => {
        this.form = data
      })
    },
    view(id){
      this.get(id)
      this.showViewModal = true
    },
    create(){

    }

  },

  computed: {

  },

  watch: {
    project (newVal) {
      this.list()
    }
  },
  data () {
    return {
      showViewModal: false,
      tableLoading: false,
      searchForm: {},
      rules: {},
      form:{
        name: "",
        clusterMasterUrl: "",
        clusterUsername: "",
        clusterCertKey: "",
        clusterCertData: ""
      },
      columns: [
        {
          title: 'ID',
          key: 'id',
          fixed: 'left'
        },
        {
          title: '名称',
          key: 'name'
        },
        {
          title: 'MasterURL',
          key: 'clusterMasterUrl'
        },
        {
          title: '用户名',
          key: 'clusterUsername'
        },
        {
          title: '创建时间',
          key: 'createdAt',
          render: (h, { row }) => {
            return (
              <span>{ dayjs(row.updateTime).format('YYYY-MM-DD HH:mm:ss') }</span>
            )
          }
        },
        {
          title: '修改时间',
          key: 'updatedAt',
          render: (h, { row }) => {
            return (
              <span>{ dayjs(row.updateTime).format('YYYY-MM-DD HH:mm:ss') }</span>
            )
          }
        },
        {
          title: '操作',
          key: 'action',
          width: 180,
          align: 'center',
          fixed: 'right',
          render: (h, { row }) => {
            return (
              <div>
                <ButtonGroup size='small'>
                   <Button onClick={ () => this.view(row.id) } >查看</Button>
                   <Button >更新</Button>
                   <Button type='error'>删除</Button>
                </ButtonGroup>
               </div>
            )
          }
        }
      ],
      data: {
        list: []
      }
    }
  },
  mounted () {
    this.list()
  }

}

</script>
