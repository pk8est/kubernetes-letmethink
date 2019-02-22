package com.pkest.lib.kubernetes.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonDeserializer.None;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.validators.CheckObjectMeta;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by wuzhonggui on 2018/12/18.
 * QQ: 2731429978
 * Email: pk8est@qq.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"apiVersion", "kind", "metadata", "spec"})
@JsonDeserialize(
        using = None.class
)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Function implements HasMetadata {
    @NotNull
    @JsonProperty("apiVersion")
    private String apiVersion = "kubeless.io/v1beta1";

    @NotNull
    @JsonProperty("kind")
    private String kind = "Function";
    @JsonProperty("metadata")
    @Valid
    @CheckObjectMeta(
            regexp = "^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*$",
            max = 253
    )
    private ObjectMeta metadata;

    @NotNull
    @JsonProperty("spec")
    private FunctionSpec spec;

    @Override
    public ObjectMeta getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(ObjectMeta objectMeta) {
        metadata = objectMeta;
    }

    @Override
    public String getKind() {
        return kind;
    }

    @Override
    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public void setApiVersion(String version) {
        apiVersion = version;
    }

    public FunctionSpec getSpec() {
        return spec;
    }

    public void setSpec(FunctionSpec spec) {
        this.spec = spec;
    }

    public Function(){

    }

    public Function(String apiVersion, String kind, ObjectMeta metadata, FunctionSpec spec) {
        this.apiVersion = apiVersion;
        this.kind = kind;
        this.metadata = metadata;
        this.spec = spec;
    }
}
