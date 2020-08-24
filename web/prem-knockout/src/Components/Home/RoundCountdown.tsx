import * as React from "react";

interface Props {
  round: any;
  calculateTimeLeft: any;
}

const RoundCountdown: React.FC<Props> = ({
  calculateTimeLeft,
  round,
}) => {
  const [timeLeft, setTimeLeft] = React.useState(
    calculateTimeLeft(round)
  );

  React.useEffect(() => {
    let countdown = setTimeout(() => {
      setTimeLeft(calculateTimeLeft(round));
    }, 1000);

    return function cleanup() {
      clearTimeout(countdown);
    };
  }, [calculateTimeLeft, timeLeft, round]);

  return (
    <>
      {timeLeft !== 0 ? (
        <>
          {timeLeft.days}d {timeLeft.hours}h {timeLeft.minutes}:
          {timeLeft.seconds}
        </>
      ) : undefined}
    </>
  );
};

export default RoundCountdown;
