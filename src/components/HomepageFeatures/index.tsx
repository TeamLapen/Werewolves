import React from 'react';
import clsx from 'clsx';
import styles from './styles.module.css';

type FeatureItem = {
  title: string;
  Svg: React.ComponentType<React.ComponentProps<'svg'>>;
  description: JSX.Element;
};

const FeatureList: FeatureItem[] = [
  {
    title: 'Extensive',
    Svg: require('@site/static/img/logo.svg').default,
    description: (
      <>
        A full overview over the features of Werewolves
      </>
    ),
  },
  {
    title: 'Werewolf',
    Svg: require('@site/static/img/logo.svg').default,

    description: (
      <>
        Dominate the World as Werewolf
      </>
    ),
  },
  {
    title: 'Powered by Vampirism',
    Svg: require('@site/static/img/logo.svg').default,
    description: (
      <>
          Extending the Vamprism factions system
      </>
    ),
  },
];

function Feature({title, Svg, description}: FeatureItem) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <Svg className={styles.featureSvg} role="img" />
      </div>
      <div className="text--center padding-horiz--md">
        <h3>{title}</h3>
        <p>{description}</p>
      </div>
    </div>
  );
}

export default function HomepageFeatures(): JSX.Element {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
