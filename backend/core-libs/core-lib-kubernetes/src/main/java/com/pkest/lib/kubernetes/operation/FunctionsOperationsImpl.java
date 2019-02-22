package com.pkest.lib.kubernetes.operation;

import com.pkest.lib.kubernetes.model.DoneableFunction;
import com.pkest.lib.kubernetes.model.Function;
import com.pkest.lib.kubernetes.model.FunctionList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.base.HasMetadataOperation;
import okhttp3.OkHttpClient;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuzhonggui on 2018/12/18.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
public class FunctionsOperationsImpl extends HasMetadataOperation<Function, FunctionList, DoneableFunction, Resource<Function, DoneableFunction>> {
    public FunctionsOperationsImpl(OkHttpClient client, Config config, String namespace) {
        this(client, config, "v1beta1", namespace, (String)null, Boolean.valueOf(true), (Function)null, (String)null, Boolean.valueOf(false), -1L, new TreeMap(), new TreeMap(), new TreeMap(), new TreeMap(), new TreeMap());
    }

    public FunctionsOperationsImpl(OkHttpClient client, Config config, String apiVersion, String namespace, String name, Boolean cascading, Function item, String resourceVersion, Boolean reloadingFromServer, long gracePeriodSeconds, Map<String, String> labels, Map<String, String> labelsNot, Map<String, String[]> labelsIn, Map<String, String[]> labelsNotIn, Map<String, String> fields) {
        super(client, config, "kubeless.io", apiVersion, "functions", namespace, name, cascading, item, resourceVersion, reloadingFromServer, gracePeriodSeconds, labels, labelsNot, labelsIn, labelsNotIn, fields);
    }
}
