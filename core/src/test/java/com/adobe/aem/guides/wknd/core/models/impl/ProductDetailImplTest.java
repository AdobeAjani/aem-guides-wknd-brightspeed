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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.models.ProductDetail;
import com.adobe.cq.wcm.core.components.commons.link.Link;
import com.adobe.cq.wcm.core.components.commons.link.LinkBuilder;
import com.adobe.cq.wcm.core.components.commons.link.LinkManager;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class ProductDetailImplTest {

    private final AemContext context = new AemContext();
    private final LinkManager linkManager = mock(LinkManager.class);
    private final LinkBuilder linkBuilder = mock(LinkBuilder.class);
    private final Link<?> link = mock(Link.class);

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(ProductDetailImpl.class);
        context.registerAdapter(SlingHttpServletRequest.class, LinkManager.class, linkManager);

        when(linkManager.get(any(Resource.class))).thenReturn(linkBuilder);
        when(linkBuilder.withLinkUrlPropertyName("ctaLink")).thenReturn(linkBuilder);
        when(linkBuilder.build()).thenReturn(link);
    }

    @Test
    void testCompleteContent() {
        Resource component = context.create().resource("/content/product-detail",
                "sling:resourceType", ProductDetailImpl.RESOURCE_TYPE,
                "productName", "Product name",
                "subheading", "Subheading",
                "price", "$10.99",
                "productDescription", "Product description",
                "ctaLabel", "Add to cart",
                "ctaLink", "/content/wknd/us/en/cart",
                "additionalDetailText", "Additional details");
        context.create().resource(component, "image",
                "sling:resourceType", "wknd/components/image",
                "fileReference", "/content/dam/wknd/product.jpg",
                "alt", "Product image");
        when(link.isValid()).thenReturn(true);
        when(link.getMappedURL()).thenReturn("/content/wknd/us/en/cart.html");

        ProductDetail productDetail = adapt(component);

        assertNotNull(productDetail);
        assertEquals("Product name", productDetail.getProductName());
        assertEquals("Subheading", productDetail.getSubheading());
        assertEquals("$10.99", productDetail.getPrice());
        assertEquals("Product description", productDetail.getProductDescription());
        assertEquals("Add to cart", productDetail.getCtaLabel());
        assertEquals("/content/wknd/us/en/cart.html", productDetail.getCtaLink());
        assertEquals("Additional details", productDetail.getAdditionalDetailText());
        assertTrue(productDetail.hasImage());
        assertTrue(productDetail.hasContent());
        assertTrue(productDetail.hasCta());
        assertFalse(productDetail.isEmpty());
    }

    @Test
    void testEmptyContent() {
        Resource component = context.create().resource("/content/product-detail",
                "sling:resourceType", ProductDetailImpl.RESOURCE_TYPE);

        ProductDetail productDetail = adapt(component);

        assertNotNull(productDetail);
        assertTrue(productDetail.isEmpty());
        assertNull(productDetail.getProductName());
        assertNull(productDetail.getSubheading());
        assertNull(productDetail.getPrice());
        assertNull(productDetail.getProductDescription());
        assertNull(productDetail.getCtaLabel());
        assertNull(productDetail.getAdditionalDetailText());
        assertFalse(productDetail.hasImage());
        assertFalse(productDetail.hasContent());
        assertFalse(productDetail.hasCta());
        assertNull(productDetail.getCtaLink());
    }

    @Test
    void testImageOnlyContent() {
        Resource component = context.create().resource("/content/product-detail",
                "sling:resourceType", ProductDetailImpl.RESOURCE_TYPE);
        context.create().resource(component, "image",
                "fileReference", "/content/dam/wknd/product.jpg");

        ProductDetail productDetail = adapt(component);

        assertTrue(productDetail.hasImage());
        assertFalse(productDetail.hasContent());
        assertFalse(productDetail.isEmpty());
    }

    @Test
    void testCtaRequiresLabelAndValidLink() {
        Resource component = context.create().resource("/content/product-detail",
                "sling:resourceType", ProductDetailImpl.RESOURCE_TYPE,
                "ctaLabel", "Add to cart",
                "ctaLink", "/content/wknd/us/en/cart");
        when(link.isValid()).thenReturn(false);

        ProductDetail productDetail = adapt(component);

        assertFalse(productDetail.hasCta());
        assertFalse(productDetail.hasContent());
        assertNull(productDetail.getCtaLink());
        assertTrue(productDetail.isEmpty());
    }

    @Test
    void testPartialTextContent() {
        Resource component = context.create().resource("/content/product-detail",
                "sling:resourceType", ProductDetailImpl.RESOURCE_TYPE,
                "productName", "Product name");

        ProductDetail productDetail = adapt(component);

        assertEquals("Product name", productDetail.getProductName());
        assertNull(productDetail.getSubheading());
        assertNull(productDetail.getPrice());
        assertNull(productDetail.getProductDescription());
        assertNull(productDetail.getCtaLabel());
        assertNull(productDetail.getCtaLink());
        assertNull(productDetail.getAdditionalDetailText());
        assertTrue(productDetail.hasContent());
        assertFalse(productDetail.isEmpty());
    }

    @Test
    void testImageChildWithoutFileReference() {
        Resource component = context.create().resource("/content/product-detail",
                "sling:resourceType", ProductDetailImpl.RESOURCE_TYPE);
        context.create().resource(component, "image",
                "sling:resourceType", "wknd/components/image",
                "alt", "Product image");

        ProductDetail productDetail = adapt(component);

        assertFalse(productDetail.hasImage());
        assertFalse(productDetail.hasContent());
        assertTrue(productDetail.isEmpty());
    }

    @Test
    void testBlankValuesAreTreatedAsMissing() {
        Resource component = context.create().resource("/content/product-detail",
                "sling:resourceType", ProductDetailImpl.RESOURCE_TYPE,
                "productName", " ",
                "subheading", "\t",
                "price", "\n",
                "productDescription", "  ",
                "ctaLabel", " ",
                "additionalDetailText", "\t");
        context.create().resource(component, "image",
                "fileReference", " ");
        when(link.isValid()).thenReturn(true);
        when(link.getMappedURL()).thenReturn(" ");

        ProductDetail productDetail = adapt(component);

        assertFalse(productDetail.hasImage());
        assertFalse(productDetail.hasCta());
        assertFalse(productDetail.hasContent());
        assertTrue(productDetail.isEmpty());
    }

    @Test
    void testCtaRequiresLabel() {
        Resource component = context.create().resource("/content/product-detail",
                "sling:resourceType", ProductDetailImpl.RESOURCE_TYPE,
                "ctaLink", "https://example.com/product");
        when(link.isValid()).thenReturn(true);
        when(link.getMappedURL()).thenReturn("https://example.com/product");

        ProductDetail productDetail = adapt(component);

        assertEquals("https://example.com/product", productDetail.getCtaLink());
        assertFalse(productDetail.hasCta());
        assertFalse(productDetail.hasContent());
        assertTrue(productDetail.isEmpty());
    }

    @Test
    void testCtaUsesMappedExternalUrl() {
        Resource component = context.create().resource("/content/product-detail",
                "sling:resourceType", ProductDetailImpl.RESOURCE_TYPE,
                "ctaLabel", "Buy now",
                "ctaLink", "https://example.com/product");
        when(link.isValid()).thenReturn(true);
        when(link.getMappedURL()).thenReturn("https://example.com/product");

        ProductDetail productDetail = adapt(component);

        assertEquals("https://example.com/product", productDetail.getCtaLink());
        assertTrue(productDetail.hasCta());
        assertTrue(productDetail.hasContent());
        assertFalse(productDetail.isEmpty());
    }

    @Test
    void testValidLinkWithBlankMappedUrlIsNotCta() {
        Resource component = context.create().resource("/content/product-detail",
                "sling:resourceType", ProductDetailImpl.RESOURCE_TYPE,
                "ctaLabel", "Buy now",
                "ctaLink", "/content/wknd/us/en/cart");
        when(link.isValid()).thenReturn(true);
        when(link.getMappedURL()).thenReturn("");

        ProductDetail productDetail = adapt(component);

        assertEquals("", productDetail.getCtaLink());
        assertFalse(productDetail.hasCta());
        assertFalse(productDetail.hasContent());
        assertTrue(productDetail.isEmpty());
    }

    private ProductDetail adapt(Resource component) {
        context.currentResource(component);
        return context.request().adaptTo(ProductDetail.class);
    }
}
