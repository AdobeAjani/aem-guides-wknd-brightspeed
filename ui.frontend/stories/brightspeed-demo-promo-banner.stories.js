import '../src/main/webpack/site/main.scss';

export default {
  title: 'Brightspeed Demo Promo Banner',
  parameters: {
    layout: 'fullscreen',
  },
  decorators: [
    (Story) => `<style>#root { box-sizing: border-box; padding: 0 !important; width: 100%; }</style>${Story()}`,
  ],
};

export const Default = () => `
  <section class="cmp-brightspeed-demo-promo-banner">
    <div class="cmp-brightspeed-demo-promo-banner__content">
      <h2 class="cmp-brightspeed-demo-promo-banner__title">Brightspeed Fiber is here</h2>
      <p class="cmp-brightspeed-demo-promo-banner__description">
        Discover fast, reliable internet built for how you live and work.
      </p>
    </div>
    <a class="cmp-brightspeed-demo-promo-banner__cta" href="/content/wknd/us/en">
      Check availability
    </a>
  </section>
`;

export const WithoutCallToAction = () => `
  <section class="cmp-brightspeed-demo-promo-banner">
    <div class="cmp-brightspeed-demo-promo-banner__content">
      <h2 class="cmp-brightspeed-demo-promo-banner__title">A brighter internet experience</h2>
      <p class="cmp-brightspeed-demo-promo-banner__description">
        Straightforward plans and dependable connectivity for your home.
      </p>
    </div>
  </section>
`;
