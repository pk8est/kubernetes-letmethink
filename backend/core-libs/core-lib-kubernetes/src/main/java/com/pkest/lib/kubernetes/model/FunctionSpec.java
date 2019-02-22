package com.pkest.lib.kubernetes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonDeserializer.None;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ServiceSpec;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.validators.CheckObjectMeta;

import javax.validation.Valid;

/**
 * Created by wuzhonggui on 2018/12/18.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"apiVersion", "kind", "metadata", "deployment", "checksum", "deps", "function", "function-content-type", "handler", "horizontalPodAutoscaler", "runtime", "service", "timeout"})
@JsonDeserialize(
        using = None.class
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionSpec {
    @JsonProperty("metadata")
    @Valid
    @CheckObjectMeta(
            regexp = "^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*$",
            max = 253
    )
    private ObjectMeta metadata;

    @JsonProperty("deployment")
    private Deployment deployment;

    @JsonProperty("checksum")
    private String checksum;

    @JsonProperty("deps")
    private String deps;

    @JsonProperty("function")
    private String function;

    @JsonProperty("function-content-type")
    private String functionContentType = "text";

    @JsonProperty("handler")
    private String handler;

    @JsonProperty("runtime")
    private String runtime;

    @JsonProperty("service")
    private ServiceSpec service;

    @JsonProperty("timeout")
    private String timeout;

    public ObjectMeta getMetadata() {
        return metadata;
    }

    public void setMetadata(ObjectMeta objectMeta) {
        metadata = objectMeta;
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getDeps() {
        return deps;
    }

    public void setDeps(String deps) {
        this.deps = deps;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFunctionContentType() {
        return functionContentType;
    }

    public void setFunctionContentType(String functionContentType) {
        this.functionContentType = functionContentType;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public ServiceSpec getService() {
        return service;
    }

    public void setService(ServiceSpec service) {
        this.service = service;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
