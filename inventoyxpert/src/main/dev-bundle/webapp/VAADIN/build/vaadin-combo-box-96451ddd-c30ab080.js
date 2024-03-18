import{inputFieldProperties as s,labelProperties as l,helperTextProperties as p,errorMessageProperties as m}from"./vaadin-text-field-0b3db014-1dcc0530.js";import{_ as r,I as a,O as i,m as b,x as c}from"./indexhtml-cd3ac634.js";const v={tagName:"vaadin-combo-box",displayName:"Combo Box",elements:[{selector:"vaadin-combo-box::part(input-field)",displayName:"Input field",properties:s},{selector:"vaadin-combo-box::part(toggle-button)",displayName:"Toggle button",properties:[r.iconColor,r.iconSize]},{selector:"vaadin-combo-box::part(label)",displayName:"Label",properties:l},{selector:"vaadin-combo-box::part(helper-text)",displayName:"Helper text",properties:p},{selector:"vaadin-combo-box::part(error-message)",displayName:"Error message",properties:m},{selector:"vaadin-combo-box-overlay::part(overlay)",displayName:"Overlay",properties:[a.backgroundColor,a.borderColor,a.borderWidth,a.borderRadius,a.padding]},{selector:"vaadin-combo-box-overlay vaadin-combo-box-item",displayName:"Overlay items",properties:[i.textColor,i.fontSize,i.fontWeight]},{selector:"vaadin-combo-box-overlay vaadin-combo-box-item::part(checkmark)::before",displayName:"Overlay item checkmark",properties:[r.iconColor,r.iconSize]}],async setupElement(o){o.overlayClass=o.getAttribute("class")},openOverlay:o=>{const e=o.element,t=e.$.overlay;b(e,e,t)},hideOverlay:o=>{const e=o.element,t=e.$.overlay;c(e,e,t)}};export{v as default};
