import React from "react";
import { Route } from "react-router-dom";

const RouteLayout2 = ({ component: Component, history, ...rest }: any) => {
  return (
    <Route
      {...rest}
      render={(matchProps) => (
        <b>
          <Component {...matchProps}></Component>
        </b>
      )}
    />
  );
};

export default RouteLayout2;
