/*
 *  Copyright 2026 Adobe Systems Incorporated
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
 * Represents the Network Expansion Campaign AEM Component for the WKND Brightspeed project.
 * This component displays information about fiber network expansion initiatives to prospective customers.
 */
public interface NetworkExpansionCampaign {

    /**
     * @return the campaign headline (e.g., "Fiber Is Coming To Nashville")
     */
    String getHeadline();

    /**
     * @return the campaign description for prospective customers
     */
    String getDescription();

    /**
     * @return the service area (city, market, or region)
     */
    String getServiceArea();

    /**
     * @return the deployment status (planning, construction, or available)
     */
    String getDeploymentStatus();

    /**
     * @return the launch date (e.g., "Q4 2026")
     */
    String getLaunchDate();

    /**
     * @return the campaign image URL
     */
    String getImage();

    /**
     * @return the image alt text for accessibility
     */
    String getImageAltText();

    /**
     * @return the CTA button text
     */
    String getCtaText();

    /**
     * @return the CTA button URL
     */
    String getCtaUrl();

    /**
     * @return the optional analytics identifier for tracking
     */
    String getAnalyticsId();

    /**
     * @return true if an image is configured
     */
    boolean hasImage();

    /**
     * @return the CSS class name for the deployment status badge
     */
    String getStatusBadgeClass();

    /**
     * @return true if the component is empty or lacks required content
     */
    boolean isEmpty();
}
