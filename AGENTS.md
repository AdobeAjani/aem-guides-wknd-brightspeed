# WKND Sites Project - Reactor Project

This is an AEM as a Cloud Service project using the Java stack.

It is built locally using Maven and can be tested against a local AEM SDK, and the Dispatcher configuration is validated locally using the Dispatcher Tools.

Production deployments are done through Adobe Cloud Manager using Full Stack Pipelines.

The Java version used in Cloud Manager pipelines is defined in the `.cloudmanager/java-version` file. Assume the same is used for local builds.

## Modules

- `all`: FileVault content package. Includes the other deployable packages and the OSGi bundle for installation.
- `core`: OSGi bundle. Contains Java backend services, Sling Models, and business logic. Uses OSGi for dependency injection and JUnit with AEM Mocks for unit testing.
- `ui.frontend`: Webpack frontend module. Compiles JavaScript, TypeScript, and SCSS, then generates AEM client libraries in `ui.apps`.
- `ui.apps`: FileVault application package. Contains components, HTL scripts, dialogs, client libraries, and application repository structure.
- `ui.apps.structure`: FileVault package defining the immutable application repository structure.
- `ui.config`: FileVault package containing environment-specific OSGi configurations.
- `ui.content`: FileVault content package containing editable templates, policies, site configuration, and baseline content.
- `ui.content.sample`: FileVault content package containing sample WKND pages, Experience Fragments, users, and assets.
- `it.tests`: Integration tests executed against running AEM environments, including Cloud Manager custom functional testing.
- `dispatcher`: Cloud-optimized Apache and Dispatcher configuration for routing, filtering, caching, invalidation, and response headers.
- `ui.tests`: Cypress end-to-end tests executed against running AEM environments, including Cloud Manager custom UI testing.

## Build

The project uses Maven as the primary build tool. Common commands:

- Full build: `mvn clean install`
- Build and deploy to a local AEM author SDK: `mvn clean install -PautoInstallSinglePackage`
- Build and deploy to a local AEM publish SDK: `mvn clean install -PautoInstallSinglePackagePublish`
- Build a single module and its dependencies: `mvn clean install -pl <module> -am`
- Build frontend production client libraries: `cd ui.frontend && npm ci && npm run prod`
- Develop frontend locally: `cd ui.frontend && npm start`
- Run Storybook: `cd ui.frontend && npm run storybook`
- Validate Dispatcher configuration: `cd dispatcher && ./bin/validate.sh src`

Use Java 21, as required by the root Maven enforcer configuration and `.cloudmanager/java-version`.

## Development guidance

- Follow AEM as a Cloud Service constraints: application code under `/apps` is immutable at runtime, while mutable content and configuration belong under `/content` and `/conf`.
- Prefer versioned AEM Core Components through `sling:resourceSuperType` instead of copying Core Component implementations.
- Keep rendering logic in Sling Models and HTL. HTL must remain presentation-focused and use its context-aware escaping.
- Resolve authored links with AEM/Core Components link APIs so internal paths are mapped correctly for publish and Dispatcher.
- Keep OSGi configurations environment-specific under `ui.config`; never hardcode secrets or environment endpoints.
- Use service users with least-privilege repository access. Do not use administrative resource resolvers.
- Keep queries bounded and indexable. Add Oak indexes through code when application queries require them.
- Add frontend code to `ui.frontend`; generated client libraries are written into `ui.apps`.
- Preserve Dispatcher deny-by-default filtering and cache-safe behavior. Do not broadly allow selectors, extensions, or paths.
- Add focused unit, integration, or UI coverage when behavior changes, and use the smallest existing validation command that covers the change.

## Important resources

Use AEM as a Cloud Service documentation in preference to AEM 6.5 or on-premises guidance.

- [Core Concepts](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/overview/architecture)
- [AEM Project Structure](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/aem-project-content-package-structure)
- [AEM Technical Foundations](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/aem-technologies)
- [AEM Development Guidelines](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/development-guidelines)
- [Java API Best Practices](https://experienceleague.adobe.com/en/docs/experience-manager-learn/foundation/development/understand-java-api-best-practices)
- [AEM as a Cloud Service SDK](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/aem-as-a-cloud-service-sdk)
- [Sling Adapters](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/full-stack/sling-adapters)
- [Sling Resource Merger](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/full-stack/sling-resource-merger)
- [Getting Started with HTL](https://experienceleague.adobe.com/en/docs/experience-manager-htl/content/getting-started)
- [Overlays](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/full-stack/overlays)
- [Editable Templates](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/full-stack/components-templates/templates)
- [Core Components Introduction](https://experienceleague.adobe.com/en/docs/experience-manager-core-components/using/introduction)
- [Components Reference Guide](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/full-stack/components-templates/reference)
- [Assets HTTP API](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/assets/admin/mac-api-assets)
- [Deprecated and Removed Features and APIs](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/release-notes/deprecated-removed-features)
- [Service User Mapping Best Practices](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/security/best-practices-for-sling-service-user-mapping-and-service-user-definition)
- [Using Client-Side Libraries](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/full-stack/clientlibs)
- [Universal Editor in AEM](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/universal-editor/getting-started)
- [Content Fragments](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/sites/administering/content-fragments/overview)
- [Experience Fragments](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/sites/authoring/fragments/experience-fragments)
- [AEM APIs for Structured Content](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/headless/apis-headless-and-content-fragments)
- [Replication](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/operations/replication)
- [Content Search and Indexing](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/operations/indexing)
- [CDN in AEM as a Cloud Service](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/content-delivery/cdn)
- [Deployment and Maintenance](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/deploying/overview)
- [API Reference Materials](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/developing/reference-materials)
- [WCM.io AEM Mocks](https://wcm.io/testing/aem-mock/)
- [Sling Mocks](https://sling.apache.org/documentation/development/sling-mock.html)
- [Validating Dispatcher Configuration](https://experienceleague.adobe.com/en/docs/experience-manager-cloud-service/content/implementing/content-delivery/validation-debug)
