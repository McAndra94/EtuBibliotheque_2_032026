import {
  MAT_SELECT_CONFIG,
  MAT_SELECT_SCROLL_STRATEGY,
  MAT_SELECT_SCROLL_STRATEGY_PROVIDER,
  MAT_SELECT_SCROLL_STRATEGY_PROVIDER_FACTORY,
  MAT_SELECT_TRIGGER,
  MatSelect,
  MatSelectChange,
  MatSelectModule,
  MatSelectTrigger
} from "./chunk-INLDLPIA.js";
import "./chunk-QXCRN72Z.js";
import {
  MatError,
  MatFormField,
  MatHint,
  MatLabel,
  MatPrefix,
  MatSuffix
} from "./chunk-VVWMZGOE.js";
import "./chunk-NUGAVD24.js";
import "./chunk-S3UJLWN4.js";
import "./chunk-UAGOGNAG.js";
import "./chunk-URTMO4QY.js";
import {
  MatOptgroup,
  MatOption
} from "./chunk-DAIOOCV5.js";
import "./chunk-5CYXRKIM.js";
import "./chunk-ZZSUQ2D6.js";
import "./chunk-YLLIG6PI.js";
import "./chunk-GYJ7YYJZ.js";
import "./chunk-O6OVHPKU.js";
import "./chunk-JYJIBUF7.js";
import "./chunk-AGZFHLKA.js";
import "./chunk-JHHVI47W.js";
import "./chunk-FEJSPTDN.js";
import "./chunk-42FJBLFI.js";
import "./chunk-GV5LUSDY.js";
import "./chunk-JIYKMQCZ.js";
import "./chunk-7OSBGEDC.js";
import "./chunk-RS4I4ECD.js";
import "./chunk-32HRFK3Z.js";
import "./chunk-2O4WY5GE.js";
import "./chunk-II4XZMED.js";
import "./chunk-UC7VERHG.js";
import "./chunk-QK4BNG7P.js";
import "./chunk-43Q4GC3Q.js";
import "./chunk-6O64XAEL.js";
import "./chunk-ZYONDSEL.js";
import "./chunk-C6FPZE6B.js";
import "./chunk-EG6OID33.js";
import "./chunk-45ZZICWG.js";
import "./chunk-NPWYXLOL.js";
import "./chunk-33T6IU7O.js";
import "./chunk-6Q4RANH6.js";
import "./chunk-FFZIAYYX.js";
import "./chunk-CXCX2JKZ.js";

// node_modules/@angular/material/fesm2022/select.mjs
var matSelectAnimations = {
  // Represents
  // trigger('transformPanelWrap', [
  //   transition('* => void', query('@transformPanel', [animateChild()], {optional: true})),
  // ])
  /**
   * This animation ensures the select's overlay panel animation (transformPanel) is called when
   * closing the select.
   * This is needed due to https://github.com/angular/angular/issues/23302
   */
  transformPanelWrap: {
    type: 7,
    name: "transformPanelWrap",
    definitions: [{
      type: 1,
      expr: "* => void",
      animation: {
        type: 11,
        selector: "@transformPanel",
        animation: [{
          type: 9,
          options: null
        }],
        options: {
          optional: true
        }
      },
      options: null
    }],
    options: {}
  },
  // Represents
  // trigger('transformPanel', [
  //   state(
  //     'void',
  //     style({
  //       opacity: 0,
  //       transform: 'scale(1, 0.8)',
  //     }),
  //   ),
  //   transition(
  //     'void => showing',
  //     animate(
  //       '120ms cubic-bezier(0, 0, 0.2, 1)',
  //       style({
  //         opacity: 1,
  //         transform: 'scale(1, 1)',
  //       }),
  //     ),
  //   ),
  //   transition('* => void', animate('100ms linear', style({opacity: 0}))),
  // ])
  /** This animation transforms the select's overlay panel on and off the page. */
  transformPanel: {
    type: 7,
    name: "transformPanel",
    definitions: [{
      type: 0,
      name: "void",
      styles: {
        type: 6,
        styles: {
          opacity: 0,
          transform: "scale(1, 0.8)"
        },
        offset: null
      }
    }, {
      type: 1,
      expr: "void => showing",
      animation: {
        type: 4,
        styles: {
          type: 6,
          styles: {
            opacity: 1,
            transform: "scale(1, 1)"
          },
          offset: null
        },
        timings: "120ms cubic-bezier(0, 0, 0.2, 1)"
      },
      options: null
    }, {
      type: 1,
      expr: "* => void",
      animation: {
        type: 4,
        styles: {
          type: 6,
          styles: {
            opacity: 0
          },
          offset: null
        },
        timings: "100ms linear"
      },
      options: null
    }],
    options: {}
  }
};
export {
  MAT_SELECT_CONFIG,
  MAT_SELECT_SCROLL_STRATEGY,
  MAT_SELECT_SCROLL_STRATEGY_PROVIDER,
  MAT_SELECT_SCROLL_STRATEGY_PROVIDER_FACTORY,
  MAT_SELECT_TRIGGER,
  MatError,
  MatFormField,
  MatHint,
  MatLabel,
  MatOptgroup,
  MatOption,
  MatPrefix,
  MatSelect,
  MatSelectChange,
  MatSelectModule,
  MatSelectTrigger,
  MatSuffix,
  matSelectAnimations
};
//# sourceMappingURL=@angular_material_select.js.map
