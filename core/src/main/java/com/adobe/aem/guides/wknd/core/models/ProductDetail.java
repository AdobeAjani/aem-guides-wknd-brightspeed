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
package com.adobe.aem.guides.wknd.core.models;

/**
 * Represents the Product Detail component.
 */
public interface ProductDetail {

    /**
     * @return the product name, or {@code null} when not configured
     */
    String getProductName();

    /**
     * @return the product subheading, or {@code null} when not configured
     */
    String getSubheading();

    /**
     * @return the author-formatted price, or {@code null} when not configured
     */
    String getPrice();

    /**
     * @return the product description, or {@code null} when not configured
     */
    String getProductDescription();

    /**
     * @return the call-to-action label, or {@code null} when not configured
     */
    String getCtaLabel();

    /**
     * @return the mapped call-to-action URL, or {@code null} when invalid
     */
    String getCtaLink();

    /**
     * @return supplemental product details, or {@code null} when not configured
     */
    String getAdditionalDetailText();

    /**
     * @return {@code true} when the embedded image has a DAM reference
     */
    boolean hasImage();

    /**
     * @return {@code true} when at least one text or CTA element can be rendered
     */
    boolean hasContent();

    /**
     * @return {@code true} when both the CTA label and a valid CTA link are configured
     */
    boolean hasCta();

    /**
     * @return {@code true} when the component has no renderable content
     */
    boolean isEmpty();
}
