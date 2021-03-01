import React from "react";
import { Route } from "react-router-dom";

const RouteLayout = ({ component: Component, history, ...rest }: any) => {
  return (
    <Route
      {...rest}
      render={(matchProps) => (
        <p>
          <Component {...matchProps}></Component>
        </p>
      )}
    />
  );
};

export default RouteLayout;
