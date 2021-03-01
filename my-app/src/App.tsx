import React from 'react';
import './App.css';
import { Redirect, Switch, BrowserRouter } from "react-router-dom";

import Page1 from "./components/pages/page1/page1";
import Page2 from "./components/pages/page2/page2";

import RouteLayout from "./components/shared/layout/route-layout";
import RouteLayout2 from "./components/shared/layout/route-layout-2";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <RouteLayout
            component={Page1}
            path={"/page1"}
          />
          <RouteLayout2
            component={Page2}
            path={"/page2"}
          />
          <Redirect
            to={"/page1"}
          />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
