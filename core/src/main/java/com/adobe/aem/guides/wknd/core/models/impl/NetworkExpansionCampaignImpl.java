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
package com.adobe.aem.guides.wknd.core.models.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.models.NetworkExpansionCampaign;

/**
 * Implementation of the Network Expansion Campaign component.
 * Manages the campaign content and provides methods for template rendering.
 */
@Model(
    adaptables = {SlingHttpServletRequest.class},
    adapters = {NetworkExpansionCampaign.class},
    resourceType = {NetworkExpansionCampaignImpl.RESOURCE_TYPE},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class NetworkExpansionCampaignImpl implements NetworkExpansionCampaign {

    protected static final String RESOURCE_TYPE = "wknd/components/network-expansion-campaign";

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkExpansionCampaignImpl.class);

    /**
     * Campaign headline text.
     */
    @ValueMapValue
    private String headline;

    /**
     * Campaign description text.
     */
    @ValueMapValue
    private String description;

    /**
     * Service area (city, market, region).
     */
    @ValueMapValue
    private String serviceArea;

    /**
     * Deployment status: planning, construction, or available.
     */
    @ValueMapValue
    private String deploymentStatus;

    /**
     * Launch date (e.g., Q4 2026).
     */
    @ValueMapValue
    private String launchDate;

    /**
     * Campaign image URL.
     */
    @ValueMapValue
    private String image;

    /**
     * Image alt text for accessibility.
     */
    @ValueMapValue
    private String imageAltText;

    /**
     * CTA button text.
     */
    @ValueMapValue
    private String ctaText;

    /**
     * CTA button URL.
     */
    @ValueMapValue
    private String ctaUrl;

    /**
     * Optional analytics identifier for tracking.
     */
    @ValueMapValue
    private String analyticsId;

    @Override
    public String getHeadline() {
        return headline;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getServiceArea() {
        return serviceArea;
    }

    @Override
    public String getDeploymentStatus() {
        return deploymentStatus;
    }

    @Override
    public String getLaunchDate() {
        return launchDate;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public String getImageAltText() {
        // Fallback to headline if alt text not provided
        return StringUtils.isNotBlank(imageAltText) ? imageAltText : headline;
    }

    @Override
    public String getCtaText() {
        return ctaText;
    }

    @Override
    public String getCtaUrl() {
        return ctaUrl;
    }

    @Override
    public String getAnalyticsId() {
        return analyticsId;
    }

    @Override
    public boolean hasImage() {
        return StringUtils.isNotBlank(image);
    }

    @Override
    public String getStatusBadgeClass() {
        if (StringUtils.isBlank(deploymentStatus)) {
            return "badge-planning"; // default
        }

        switch (deploymentStatus.toLowerCase()) {
            case "planning":
                return "badge-planning";
            case "construction":
                return "badge-construction";
            case "available":
                return "badge-available";
            default:
                LOGGER.warn("Unknown deployment status: {}", deploymentStatus);
                return "badge-planning";
        }
    }

    @Override
    public boolean isEmpty() {
        // Component is empty if required fields are missing
        if (StringUtils.isBlank(headline) || StringUtils.isBlank(deploymentStatus)
                || StringUtils.isBlank(launchDate) || StringUtils.isBlank(serviceArea)
                || StringUtils.isBlank(ctaText) || StringUtils.isBlank(ctaUrl)) {
            return true;
        }
        return false;
    }
}
