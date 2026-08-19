/*
 *  Copyright 2026 Adobe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.guides.wknd.core.models.impl;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.core.models.ProductDetail;
import com.adobe.cq.wcm.core.components.commons.link.Link;
import com.adobe.cq.wcm.core.components.commons.link.LinkManager;

/**
 * Sling Model implementation for the Product Detail component.
 */
@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = ProductDetail.class,
        resourceType = ProductDetailImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ProductDetailImpl implements ProductDetail {

    protected static final String RESOURCE_TYPE = "wknd/components/product-detail";
    private static final String PN_CTA_LINK = "ctaLink";
    private static final String PN_FILE_REFERENCE = "fileReference";

    @Self
    private SlingHttpServletRequest request;

    @ChildResource(name = "image")
    private Resource imageResource;

    @ValueMapValue
    private String productName;

    @ValueMapValue
    private String subheading;

    @ValueMapValue
    private String price;

    @ValueMapValue
    private String productDescription;

    @ValueMapValue
    private String ctaLabel;

    @ValueMapValue
    private String additionalDetailText;

    private Link<?> ctaLink;

    @PostConstruct
    protected void init() {
        LinkManager linkManager = request != null ? request.adaptTo(LinkManager.class) : null;
        if (linkManager != null) {
            ctaLink = linkManager.get(request.getResource())
                    .withLinkUrlPropertyName(PN_CTA_LINK)
                    .build();
        }
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public String getSubheading() {
        return subheading;
    }

    @Override
    public String getPrice() {
        return price;
    }

    @Override
    public String getProductDescription() {
        return productDescription;
    }

    @Override
    public String getCtaLabel() {
        return ctaLabel;
    }

    @Override
    public String getCtaLink() {
        return ctaLink != null && ctaLink.isValid() ? ctaLink.getMappedURL() : null;
    }

    @Override
    public String getAdditionalDetailText() {
        return additionalDetailText;
    }

    @Override
    public boolean hasImage() {
        return imageResource != null
                && isNotBlank(imageResource.getValueMap().get(PN_FILE_REFERENCE, String.class));
    }

    @Override
    public boolean hasContent() {
        return hasCta()
                || isNotBlank(productName)
                || isNotBlank(subheading)
                || isNotBlank(price)
                || isNotBlank(productDescription)
                || isNotBlank(additionalDetailText);
    }

    @Override
    public boolean hasCta() {
        return isNotBlank(ctaLabel) && isNotBlank(getCtaLink());
    }

    @Override
    public boolean isEmpty() {
        return !hasImage() && !hasContent();
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
