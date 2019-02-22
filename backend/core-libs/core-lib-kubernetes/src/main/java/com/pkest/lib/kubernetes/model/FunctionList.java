package com.pkest.lib.kubernetes.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonDeserializer.None;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.api.model.ListMeta;
import io.fabric8.kubernetes.api.model.apps.Deployment;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"apiVersion", "kind", "metadata", "items"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(
        using = None.class
)
public class FunctionList implements KubernetesResource, KubernetesResourceList {
    @NotNull
    @JsonProperty("apiVersion")
    private String apiVersion = "kubeless.io/v1beta1";
    @JsonProperty("items")
    @Valid
    private List<Deployment> items = new ArrayList();
    @NotNull
    @JsonProperty("kind")
    private String kind = "FunctionList";
    @JsonProperty("metadata")
    @Valid
    private ListMeta metadata;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap();

    public FunctionList() {
    }

    public FunctionList(String apiVersion, List<Deployment> items, String kind, ListMeta metadata) {
        this.apiVersion = apiVersion;
        this.items = items;
        this.kind = kind;
        this.metadata = metadata;
    }

    @JsonProperty("apiVersion")
    public String getApiVersion() {
        return this.apiVersion;
    }

    @JsonProperty("apiVersion")
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @JsonProperty("items")
    public List<Deployment> getItems() {
        return this.items;
    }

    @JsonProperty("items")
    public void setItems(List<Deployment> items) {
        this.items = items;
    }

    @JsonProperty("kind")
    public String getKind() {
        return this.kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("metadata")
    public ListMeta getMetadata() {
        return this.metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(ListMeta metadata) {
        this.metadata = metadata;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String toString() {
        return "DeploymentList(apiVersion=" + this.getApiVersion() + ", items=" + this.getItems() + ", kind=" + this.getKind() + ", metadata=" + this.getMetadata() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof io.fabric8.kubernetes.api.model.apps.DeploymentList)) {
            return false;
        } else {
            FunctionList other = (FunctionList)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                label71: {
                    String this$apiVersion = this.getApiVersion();
                    String other$apiVersion = other.getApiVersion();
                    if(this$apiVersion == null) {
                        if(other$apiVersion == null) {
                            break label71;
                        }
                    } else if(this$apiVersion.equals(other$apiVersion)) {
                        break label71;
                    }

                    return false;
                }

                List this$items = this.getItems();
                List other$items = other.getItems();
                if(this$items == null) {
                    if(other$items != null) {
                        return false;
                    }
                } else if(!this$items.equals(other$items)) {
                    return false;
                }

                label57: {
                    String this$kind = this.getKind();
                    String other$kind = other.getKind();
                    if(this$kind == null) {
                        if(other$kind == null) {
                            break label57;
                        }
                    } else if(this$kind.equals(other$kind)) {
                        break label57;
                    }

                    return false;
                }

                ListMeta this$metadata = this.getMetadata();
                ListMeta other$metadata = other.getMetadata();
                if(this$metadata == null) {
                    if(other$metadata != null) {
                        return false;
                    }
                } else if(!this$metadata.equals(other$metadata)) {
                    return false;
                }

                Map this$additionalProperties = this.getAdditionalProperties();
                Map other$additionalProperties = other.getAdditionalProperties();
                if(this$additionalProperties == null) {
                    if(other$additionalProperties == null) {
                        return true;
                    }
                } else if(this$additionalProperties.equals(other$additionalProperties)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof io.fabric8.kubernetes.api.model.apps.DeploymentList;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        String $apiVersion = this.getApiVersion();
        int result1 = result * 59 + ($apiVersion == null?43:$apiVersion.hashCode());
        List $items = this.getItems();
        result1 = result1 * 59 + ($items == null?43:$items.hashCode());
        String $kind = this.getKind();
        result1 = result1 * 59 + ($kind == null?43:$kind.hashCode());
        ListMeta $metadata = this.getMetadata();
        result1 = result1 * 59 + ($metadata == null?43:$metadata.hashCode());
        Map $additionalProperties = this.getAdditionalProperties();
        result1 = result1 * 59 + ($additionalProperties == null?43:$additionalProperties.hashCode());
        return result1;
    }
}