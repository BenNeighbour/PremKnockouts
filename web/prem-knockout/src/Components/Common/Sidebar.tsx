import * as React from "react";
import { Layout, Menu } from "antd";
import { HomeOutlined, UserOutlined, BookOutlined } from "@ant-design/icons";
import { RouteComponentProps } from "react-router-dom";

const { Sider } = Layout;

interface Props extends RouteComponentProps<any> {
  current: string;
}

const Sidebar: React.FC<Props> = (props) => {
  let [isCollapsed, setCollapsed] = React.useState(
    window.screen.width >= 400 ? false : true
  );

  let toggleCollapsed: any = () => {
    setCollapsed(!isCollapsed);
  };

  return (
    <Sider
      breakpoint="lg"
      collapsible={window.screen.width >= 400 ? true : false}
      theme="dark"
      collapsed={isCollapsed}
      onCollapse={toggleCollapsed}
    >
      <div className="logo" />

      <Menu mode="inline" theme="dark" defaultSelectedKeys={[props.current]}>
        <Menu.Item
          icon={<HomeOutlined />}
          onClick={() => {
            props.history.push("/app/home");
          }}
          key="home"
        >
          Home
        </Menu.Item>
        <Menu.Item
          icon={<UserOutlined />}
          onClick={() => {
            props.history.push("/app/profile");
          }}
          key="profile"
        >
          Your Profile
        </Menu.Item>
        <Menu.Item
          icon={<BookOutlined />}
          onClick={() => {
            props.history.push("/app/rules");
          }}
          key="rules"
        >
          Game Rules
        </Menu.Item>
      </Menu>
    </Sider>
  );
};

export default Sidebar;
